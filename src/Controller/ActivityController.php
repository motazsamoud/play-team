<?php

namespace App\Controller;

use App\Entity\Activity;
use App\Entity\Category;
use App\Form\ActivityType;
use App\Form\CategoryType;
use App\Repository\ActivityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/activity')]
class ActivityController extends AbstractController
{
    #[Route('/', name: 'app_activity_index', methods: ['GET'])]
    public function index(ActivityRepository $activityRepository): Response
    {
        return $this->render('backOffice/activity/index.html.twig', [
            'activities' => $activityRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_activity_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ActivityRepository $activityRepository): Response
    {
        $activity = new Activity();
        $form = $this->createForm(ActivityType::class, $activity);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $activityRepository->save($activity, true);
            return $this->redirectToRoute('app_activity_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('backOffice/activity/new.html.twig', [
            'activity' => $activity,
            'form' => $form,
        ]);
    }



    #[Route('updateActivity/{id}', name: 'updateActivity')]
    public function updateActivity(ManagerRegistry $doctrine,$id,Request $req): Response {
        $em = $doctrine->getManager();
        $activity = $doctrine->getRepository(Activity::class)->find($id);
        $form = $this->createForm(ActivityType::class,$activity);
        $form->handleRequest($req);
        if($form->isSubmitted()){
            $em->persist($activity);
            $em->flush();
            return $this->redirectToRoute('app_activity_index');
        }
        return $this->renderForm('backOffice/activity/new.html.twig',['form'=>$form]);

    }
    #[Route('deleteActivity/{id}', name: 'deleteActivity')]
    public function deleteActivity(ManagerRegistry $doctrine,$id): Response
    {
        $em= $doctrine->getManager();
        $S= $doctrine->getRepository(Activity::class)->find($id);
        $em->remove($S);
        $em->flush();
        return $this->redirectToRoute('app_activity_index');
    }
}
