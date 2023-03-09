<?php

namespace App\Controller;

use App\Entity\Activity;

use App\Repository\ActivityRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoder;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\Validator\Constraints\UserPassword;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Validator\Constraints\Date;


/**
 * @Route ("/jsonactivity")
 */
class activitejson extends AbstractController
{
    ######################################afficher tous les reclamations et les offres###########################
    /**
     * @Route("/activity/liste")
     * @throws ExceptionInterface
     */
    public function listeactivity(ActivityRepository $activityRepository, NormalizerInterface $normalizer)
    {
        $activitys = $activityRepository->findAll();
        $jsonContent = $normalizer->normalize($activitys, 'json', ['groups' => 'post:read']);

        return new Response(json_encode($jsonContent), Response::HTTP_OK, ['Content-Type' => 'application/json']);
    }





###########################################################################################################
##############afficher par id ##########################################################################

    /**
     * @Route("/activity/lire/{id}")
     */
    public function participationId(Request $request,$id,NormalizerInterface $Normalizer)
    {

        $em = $this -> getDoctrine()->getManager();
        $participation =$em->getRepository(Activity::class)->find($id);
        $jsonContent =$Normalizer->normalize($participation,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

}


###########################################################################################################
##############ajouter ##########################################################################




    /**
     * @Route("/activity/ajout")
     */
    public function ajoutPqrticipation(Request $request,NormalizerInterface $normalizer, UserPasswordEncoderInterface $activityPasswordEncoder)
    {
        // On vérifie si la requête est une requête Ajax

        $em =$this->getDoctrine()->getManager();
        $activity = new Activity();

        $activity->setNom($request->get('nom')) ;
        $activity->setDescription($request->get('description')) ;
        $activity->setCategorie($request->get('categorie')) ;

// On sauegarde en base
        $em->persist($activity);
        $em->flush();
        $jsonContent = $normalizer->normalize($activity,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));


    }




###########################################################################################################
##############supprimer ##########################################################################




    /**
     * @Route("/activity/supprimer/{id}")
     */
    public function supprimParticipation(Request $request,SerializerInterface  $serializer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $activity=$em->getRepository(Activity::class)->find($id);
        $em->remove($activity);
        $em->flush();
        $jsonContent = $serializer->serialize($activity, 'json', [
            'circular_reference_handler' => function ($object) {
                return $object->getIdUser();
            }
        ]);

        // On instancie la réponse
        $response = new Response($jsonContent);

        // On ajoute l'entête HTTP
        $response->headers->set('Content-Type', 'application/json');

        // On envoie la réponse
        return $response;


    }


###########################################################################################################
##############modifier ##########################################################################


    /**
     * @Route("/activity/modif/{id}")
     */
    public function modifactivity(Request $request, SerializerInterface $serializer, $id, UserPasswordEncoderInterface $activityPasswordEncoder)
    {
        $em = $this -> getDoctrine()->getManager();
        $activity = $em->getRepository(Activity::class)->find($id);
        // On hydrate l'objet

        $activity->setNom($request->get('nom')) ;
        $activity->setDescription($request->get('description')) ;
        $activity->setCategorie($request->get('categorie')) ;

        $em->flush();
        $jsonContent = $serializer->serialize($activity, 'json', [
            'circular_reference_handler' => function ($object) {
                return $object->getIdUser();
            }
        ]);


        // On instancie la réponse
        $response = new Response($jsonContent);

        // On ajoute l'entête HTTP
        $response->headers->set('Content-Type', 'application/json');

        // On envoie la réponse
        return $response;

    }












    /**
     * @Route("/activity/liste/{id}")
     */
    public function listeActivityparNom(ActivityRepository $activityRepository ,NormalizerInterface $Normalizer,$id)
    {
        $jsonContent=array();
        $entityManager = $this->getDoctrine()->getManager();
        $activity = $entityManager->getRepository(Activity::class)->findAll() ;
        $output=[];
        foreach ($activity as $plc){
            if($plc->getNom()==$id){

                $jsonContent1 = $Normalizer->normalize($plc) ;
                array_push($jsonContent,$jsonContent1);}
        }


        return new Response(json_encode($jsonContent));

    }







}
