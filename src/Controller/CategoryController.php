<?php

namespace App\Controller;

use App\Entity\Category;
use App\Entity\Speciality;
use App\Form\CategoryType;
use App\Form\SpecialityType;
use App\Repository\CategoryRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

#[Route('/category')]
class CategoryController extends AbstractController
{
    #[Route('/', name: 'app_category_index', methods: ['GET'])]
    public function index(CategoryRepository $categoryRepository): Response
    {
        return $this->render('backOffice/category/index.html.twig', [
            'categories' => $categoryRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_category_new', methods: ['GET', 'POST'])]
    public function new(Request $request, CategoryRepository $categoryRepository,SluggerInterface $slugger): Response
    {
        $category = new Category();
        $form = $this->createForm(CategoryType::class, $category);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var UploadedFile $eventImage */
            $eventImage = $form->get('image')->getData(); //Cette ligne récupère le fichier téléchargé par l'utilisateur via le formulaire. Le champ de formulaire associé à l'image est nommé "image".

            // Cette ligne vérifie si un fichier d'image a été téléchargé.
            if ($eventImage) {
                $originalFilename = pathinfo($eventImage->getClientOriginalName(), PATHINFO_FILENAME);
                // Cette ligne extrait le nom de fichier d'origine de l'image téléchargée.
                $safeFilename = $slugger->slug($originalFilename);
                //Cette ligne utilise l'interface SluggerInterface pour générer un nom de fichier sécurisé à partir du nom de fichier d'origine.
                $newFilename = $safeFilename . '-' . uniqid() . '.' . $eventImage->guessExtension();

                //Cette ligne génère un nouveau nom de fichier unique pour l'image téléchargée 
                //en combinant le nom de fichier sécurisé avec un identifiant unique et l'extension de fichier.
                try //Ce bloc d'instructions tente de déplacer le fichier téléchargé vers le répertoire de stockage des images.
                 {
                    $eventImage->move(
                        $this->getParameter('image_directory'),
                        $newFilename
                        //Cette ligne déplace le fichier vers le répertoire de stockage des images. La méthode getParameter est
                        // utilisée pour récupérer le chemin du répertoire de stockage à partir du fichier de configuration Symfony.
                    );
                } catch (FileException $e) {
                    // Si une exception est levée lors du déplacement du fichier, le code dans ce bloc d'instructions sera exécuté.
                }

                // updates the 'eventImage' property to store the image file name
                // instead of its contents
                
                $category->setImage($newFilename);
                //Cette ligne définit la propriété "image" de l'objet Category avec le nom de fichier généré pour l'image téléchargée.
            }
            $categoryRepository->save($category, true);
            //Cette ligne enregistre la nouvelle
            return $this->redirectToRoute('app_category_index', [], Response::HTTP_SEE_OTHER);
            //Cette ligne renvoie l'utilisateur vers la page d'index des catégories.
        }
        return $this->renderForm('backOffice/category/new.html.twig', [
            'category' => $category,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_category_show', methods: ['GET'])]
    public function show(Category $category): Response
    {
        return $this->render('backOffice/category/show.html.twig', [
            'category' => $category,
        ]);
    }

    #[Route('updateCategory/{id}', name: 'updateCategory')]
    public function updateCategory(ManagerRegistry $doctrine,$id,Request $req): Response {
        $em = $doctrine->getManager();
        $speciality = $doctrine->getRepository(Category::class)->find($id);
        $form = $this->createForm(CategoryType::class,$speciality);
        $form->handleRequest($req);
        if($form->isSubmitted()){
            $em->persist($speciality);
            $em->flush();
            return $this->redirectToRoute('app_category_index');
        }
        return $this->renderForm('backOffice/category/new.html.twig',['form'=>$form]);

    }

    #[Route('deleteCategory/{id}', name: 'deleteCategory')]
    public function deleteCategory(ManagerRegistry $doctrine,$id): Response
    {
        $em= $doctrine->getManager();
        $S= $doctrine->getRepository(Category::class)->find($id);
        $em->remove($S);
        $em->flush();
        return $this->redirectToRoute('app_category_index');
    }
}
