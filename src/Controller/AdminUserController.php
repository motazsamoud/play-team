<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\PaginatorInterface;


#[Route('/admin/user')]
class AdminUserController extends AbstractController

{

    
    #[Route('/', name: 'app_admin_user_index', methods: ['GET'])]
    public function index(Request $request,UserRepository $userRepository, PaginatorInterface $paginator): Response
    {
        $users = $userRepository->findAll();
        $users = $paginator->paginate(
            $users, /* query NOT result */
            $request->query->getInt('page', 1),
            10);
        return $this->render('admin_user/index.html.twig', [
            'users' => $users,
        ]);
    }

    

    #[Route('/new', name: 'app_admin_user_new', methods: ['GET', 'POST'])]
    public function new(Request $request, UserRepository $userRepository, UserPasswordHasherInterface $passwordHasher): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $plainPassword = $form->get('plainPassword')->getData();
            $hashPassword = $passwordHasher->hashPassword($user,$plainPassword);
            $user->setPassword($hashPassword);
            $userRepository->save($user, true);

            return $this->redirectToRoute('app_admin_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('admin_user/new.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_admin_user_show', methods: ['GET'])]
    public function show(User $user): Response
    {
        return $this->render('admin_user/show.html.twig', [
            'user' => $user,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_admin_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, UserRepository $userRepository, UserPasswordHasherInterface $passwordHasher): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $plainPassword = $form->get('plainPassword')->getData();
            $hashPassword = $passwordHasher->hashPassword($user,$plainPassword);
            $user->setPassword($hashPassword);
            $userRepository->save($user, true);

            return $this->redirectToRoute('app_admin_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('admin_user/edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_admin_user_delete', methods: ['POST'])]
    public function delete(Request $request, User $user, UserRepository $userRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $userRepository->remove($user, true);
        }

        return $this->redirectToRoute('app_admin_user_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/User/Status/{id}', name: 'Status')]
    public function DisableOrEnableUser(ManagerRegistry $doctrine, $id): Response
    {
        $em = $doctrine->getManager();
        $repo = $doctrine->getRepository(user::class);
        $User = $repo->find($id);
    
        if ($User->getStatus() === 'enabled') {
            $User->setStatus('disabled');
        } elseif ($User->getStatus() === 'disabled') {
            $User->setStatus('enabled');
        }
    
        $em->persist($User);
        $em->flush();
    
        return $this->redirectToRoute('app_admin_user_index');
    }

    #[Route('/search-users', name:'search_users')]
    
   public function search(Request $request, UserRepository $userRepository): Response
   {
       $keyword = $request->query->get('keyword');
       $users = $userRepository->search($keyword);

       return $this->render('user/search.html.twig', [
           'users' => $users,
       ]);
   }


}
