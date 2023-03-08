<?php

namespace App\Controller;

use App\Entity\Article;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;


class LikesController extends AbstractController
{
    private $entityManager;
    
    public function __construct(EntityManagerInterface $entityManager, UrlGeneratorInterface $urlGenerator){
        $this->entityManager = $entityManager;
        $this->urlGenerator = $urlGenerator;
    }
    
    #[Route('/likes/{id}', name: 'app_likes')]
    public function index($id, ManagerRegistry $doctrine): Response
    
    {
        $article = $this->entityManager->getRepository(Article::class)->findOneById($id);
        $entityManager = $doctrine->getManager();
       
        $likes = $article->getLikes();

        if($article->getLikes() === '0 like') 
        {
            $article->setLikes('like');
        }
        elseif ($article->getLikes() === 'like') 
        {
            $article->setLikes('dislike');
        } 
        elseif ($article->getLikes() === 'dislike') 
        {
            $article->setLikes('0 like');
        }

        
        // $plusDeLikes = $likes +1;
        // $article->setLikes($plusDeLikes);
        $entityManager->flush();
        $comments = $article->getComments();
        return $this->render('article/show.html.twig', [
            'article' => $article,
            'comments' => $comments
        ]);
    }
}
