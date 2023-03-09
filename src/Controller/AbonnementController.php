<?php

namespace App\Controller;

use App\Entity\Abonnement;
use App\Form\AbonnementType;
use App\Repository\AbonnementRepository;
use App\Repository\JeuxRepository;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\String\Slugger\SluggerInterface;

class AbonnementController extends AbstractController
{
    #[Route('/abonnement', name: 'app_abonnement')]
    public function index(): Response
    {
        return $this->render('admin.html.twig', [
            'controller_name' => 'AbonnementController',
        ]);
    }
    #[Route('/add_abonnement', name: 'add_abonnement')]

    public function Add(Request  $request , ManagerRegistry $doctrine ,SluggerInterface $slugger) : Response {

        $Abonnement =  new Abonnement() ;
        $form =  $this->createForm(AbonnementType::class,$Abonnement) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            $brochureFile = $form->get('image')->getData();
            //$file =$Abonnement->getImage();
            $originalFilename = pathinfo($brochureFile->getClientOriginalName(), PATHINFO_FILENAME);
            //$uploads_directory = $this->getParameter('upload_directory');
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $safeFilename.'-'.uniqid().'.'.$brochureFile->guessExtension();
            //$fileName = md5(uniqid()).'.'.$file->guessExtension();
            $brochureFile->move(
                $this->getParameter('upload_directory'),
                $newFilename
            );
            $Abonnement->setImage(($newFilename));
            $Abonnement = $form->getData();
            $em= $doctrine->getManager() ;
            $em->persist($Abonnement);
            $em->flush();
            return $this ->redirectToRoute('add_abonnement') ;
        }
        return $this->render('abonnement/addabonnements.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_ab', name: 'afficher_ab')]
    public function AfficheAbonnement (AbonnementRepository $repo ,PaginatorInterface $paginator ,Request $request   ): Response
    {
        //$repo=$this ->getDoctrine()->getRepository(Abonnement::class) ;
        $Abonnement=$repo->findAll() ;
        $pagination = $paginator->paginate(
            $Abonnement,
            $request->query->getInt('page', 1),
            3 // items per page
        );
        return $this->render('abonnement/index.html.twig' , [
            'Abonnement' => $pagination ,
            'ajoutA' => $Abonnement
        ]) ;
    }

    #[Route('/delete_ab/{id}', name: 'delete_ab')]
    public function Delete($id,AbonnementRepository $repository , ManagerRegistry $doctrine) : Response {
        $Abonnement=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Abonnement);
        $em->flush();
        return $this->redirectToRoute("afficher_ab") ;

    }
    #[Route('/update_ab/{id}', name: 'update_ab')]
    function update(AbonnementRepository $repo,$id,Request $request , ManagerRegistry $doctrine){
        $Abonnement = $repo->find($id) ;
        $form=$this->createForm(AbonnementType::class,$Abonnement) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){

            $Abonnement = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_ab') ;
        }
        return $this->render('abonnement/updateabonnements.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }

    #[Route('/statsabonn', name: 'statsabonn')]
    public function statistiques(AbonnementRepository $abonnrepo){
        // On va chercher toutes les catégories
        $abonn = $abonnrepo->findAll();

        $abonnName = [];
        $abonnColor = [];
        $abonnCount = [];

        // On "démonte" les données pour les séparer tel qu'attendu par ChartJS
        foreach($abonn as $abon){
            $abonnName[] = $abon->getName();
            $abonnColor[] = $abon->getColor();
            $abonnCount[] = count($abon->getAdherents());
        }

        // On va chercher le nombre d'annonces publiées par date

        return $this->render('stat/stats.html.twig', [
            'abonnName' => json_encode($abonnName),
            'abonnColor' => json_encode($abonnColor),
            'abonnCount' => json_encode($abonnCount),
        ]);
    }
}
