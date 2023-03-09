<?php

namespace App\Controller;

use App\Entity\Abonnement;

use App\Repository\AbonnementRepository;

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
 * @Route ("/jsonabonnement")
 */
class abonnementjson extends AbstractController
{
    ######################################afficher tous les reclamations et les offres###########################
    /**
     * @Route("/abonnement/liste")
     * @throws ExceptionInterface
     */
    public function listeabonnement(AbonnementRepository $abonnementRepository, NormalizerInterface $normalizer)
    {
        $abonnements = $abonnementRepository->findAll();
        $jsonContent = $normalizer->normalize($abonnements, 'json', ['groups' => 'post:read']);

        return new Response(json_encode($jsonContent), Response::HTTP_OK, ['Content-Type' => 'application/json']);
    }





###########################################################################################################
##############afficher par id ##########################################################################

    /**
     * @Route("/abonnement/lire/{id}")
     */
    public function participationId(Request $request,$id,NormalizerInterface $Normalizer)
    {

        $em = $this -> getDoctrine()->getManager();
        $participation =$em->getRepository(Abonnement::class)->find($id);
        $jsonContent =$Normalizer->normalize($participation,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

}


###########################################################################################################
##############ajouter ##########################################################################




    /**
     * @Route("/abonnement/ajout")
     */
    public function ajoutPqrticipation(Request $request,NormalizerInterface $normalizer, UserPasswordEncoderInterface $abonnementPasswordEncoder)
    {
        // On vérifie si la requête est une requête Ajax

        $em =$this->getDoctrine()->getManager();
        $abonnement = new Abonnement();
        $dateString = $request->get('date');
        $date = \DateTime::createFromFormat('D M d H:i:s T Y', $dateString);
        $abonnement->setDate($date) ;
        $abonnement->setDuree($request->get('duree')) ;
        $abonnement->setPrixA($request->get('prixA')) ;
        $abonnement->setDescription($request->get('description')) ;
        $abonnement->setImage($request->get('image')) ;
        $abonnement->setName($request->get('name')) ;
// On sauegarde en base
        $em->persist($abonnement);
        $em->flush();
        $jsonContent = $normalizer->normalize($abonnement,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));


    }




###########################################################################################################
##############supprimer ##########################################################################




    /**
     * @Route("/abonnement/supprimer/{id}")
     */
    public function supprimParticipation(Request $request,SerializerInterface  $serializer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $abonnement=$em->getRepository(Abonnement::class)->find($id);
        $em->remove($abonnement);
        $em->flush();
        $jsonContent = $serializer->serialize($abonnement, 'json', [
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
     * @Route("/abonnement/modif/{id}")
     */
    public function modifabonnement(Request $request, SerializerInterface $serializer, $id, UserPasswordEncoderInterface $abonnementPasswordEncoder)
    {
        $em = $this -> getDoctrine()->getManager();
        $abonnement = $em->getRepository(Abonnement::class)->find($id);
        // On hydrate l'objet
        $dateString = $request->get('date');
        $date = \DateTime::createFromFormat('D M d H:i:s T Y', $dateString);
        $abonnement->setDate($date) ;
        $abonnement->setDuree($request->get('duree')) ;
        $abonnement->setPrixA($request->get('prixA')) ;
        $abonnement->setDescription($request->get('description')) ;
        $abonnement->setImage($request->get('image')) ;
        $abonnement->setName($request->get('name')) ;


        $em->flush();
        $jsonContent = $serializer->serialize($abonnement, 'json', [
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
     * @Route("/abonnement/liste/{id}")
     */
    public function listeabonnementparNom(AbonnementRepository $abonnementRepository ,NormalizerInterface $Normalizer,$id)
    {
        $jsonContent=array();
        $entityManager = $this->getDoctrine()->getManager();
        $abonnement = $entityManager->getRepository(Abonnement::class)->findAll() ;
        $output=[];
        foreach ($abonnement as $plc){
            if($plc->getName()==$id){

                $jsonContent1 = $Normalizer->normalize($plc) ;
                array_push($jsonContent,$jsonContent1);}
        }


        return new Response(json_encode($jsonContent));

    }







}
