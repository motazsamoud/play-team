<?php

namespace App\Controller;

use App\Repository\ActivityRepository;
use App\Repository\CategoryRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HomeController extends AbstractController
{
    #[Route('/home', name: 'home')]
    public function index(): Response
    {
        return $this->render('home/index.html.twig', [
            'controller_name' => 'HomeController',
        ]);
    }

    #[Route('/categoryFront', name: 'categoryFront', methods: ['GET'])]
    public function indexFront(CategoryRepository $categoryRepository): Response
    {
        return $this->render('category/category.html.twig', [
            'categories' => $categoryRepository->findAll(),
        ]);
    }
    #[Route('/activityFront', name: 'activityFront', methods: ['GET'])]
    public function activityFront(ActivityRepository  $activityRepository): Response
    {
        return $this->render('activity/activity.html.twig', [
            'activities' => $activityRepository->findAll(),
        ]);
    }
}
