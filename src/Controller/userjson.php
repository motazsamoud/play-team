<?php

namespace App\Controller;

use App\Entity\User;

use App\Repository\UserRepository;

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
 * @Route ("/jsonuser")
 */
class userjson extends AbstractController
{
    ######################################afficher tous les reclamations et les offres###########################
    /**
     * @Route("/user/liste")
     * @throws ExceptionInterface
     */
    public function listeUser(UserRepository $userRepository, NormalizerInterface $normalizer)
    {
        $users = $userRepository->findAll();
        $jsonContent = $normalizer->normalize($users, 'json', ['groups' => 'post:read']);

        return new Response(json_encode($jsonContent), Response::HTTP_OK, ['Content-Type' => 'application/json']);
    }





###########################################################################################################
##############afficher par id ##########################################################################

    /**
     * @Route("/user/lire/{id}")
     */
    public function participationId(Request $request,$id,NormalizerInterface $Normalizer)
    {

        $em = $this -> getDoctrine()->getManager();
        $participation =$em->getRepository(User::class)->find($id);
        $jsonContent =$Normalizer->normalize($participation,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

}


###########################################################################################################
##############ajouter ##########################################################################




    /**
     * @Route("/user/ajout")
     */
    public function ajoutPqrticipation(Request $request,NormalizerInterface $normalizer, UserPasswordEncoderInterface $userPasswordEncoder)
    {
        // On vérifie si la requête est une requête Ajax

        $em =$this->getDoctrine()->getManager();
        $User = new User();

        $User->setFirstName($request->get('first_name')) ;
        $User->setLastName($request->get('last_name')) ;
        $User->setEmail($request->get('email')) ;

        $User->setPassword($userPasswordEncoder->encodePassword(
            $User,
            $request->get('password')));
        $User->setPhoneNumber($request->get('phone_number')) ;
        $User->setImage($request->get('image')) ;
        $User->setStatus($request->get('status')) ;
        $User->setRoles(["ROLE_USER"]);
        $User->setIsVerified(0 ) ;


// On sauvegarde en base
        $em->persist($User);
        $em->flush();
        $jsonContent = $normalizer->normalize($User,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));


    }




###########################################################################################################
##############supprimer ##########################################################################




    /**
     * @Route("/user/supprimer/{id}")
     */
    public function supprimParticipation(Request $request,SerializerInterface  $serializer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $User=$em->getRepository(User::class)->find($id);
        $em->remove($User);
        $em->flush();
        $jsonContent = $serializer->serialize($User, 'json', [
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
     * @Route("/user/modif/{id}")
     */
    public function modifUser(Request $request, SerializerInterface $serializer, $id, UserPasswordEncoderInterface $userPasswordEncoder)
    {
        $em = $this -> getDoctrine()->getManager();
        $User = $em->getRepository(User::class)->find($id);
        // On hydrate l'objet
        $User->setFirstName($request->get('first_name')) ;
        $User->setLastName($request->get('last_name')) ;
        $User->setEmail($request->get('email')) ;

        $User->setPassword($userPasswordEncoder->encodePassword(
            $User,
            $request->get('password')));
        $User->setPhoneNumber($request->get('phone_number')) ;
        $User->setImage($request->get('image')) ;
        $User->setStatus($request->get('status')) ;
        $User->setRoles(["ROLE_USER"]);
        $User->setIsVerified(0 ) ;


        $em->flush();
        $jsonContent = $serializer->serialize($User, 'json', [
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
     * @Route("/loginMobile", name="loginMobile")
     */
    public function login(NormalizerInterface $normalizer, Request $request, UserPasswordEncoderInterface $userPasswordEncoder)
    {

        $user = new User();
        $email = $request->query->get('email');
        $password = $request->query->get('password');
        $em=$this->getDoctrine()->getManager();
        $user =$em->getRepository(User::class)->findOneBy(['email'=>$email]);
//var_dump($userPasswordEncoder->isPasswordValid($user,$password));

            if ($userPasswordEncoder->isPasswordValid($user,$password)) {
                $jsonContent = $normalizer->normalize($user, 'json',['groups' => 'post:read']);
                return new Response(json_encode($jsonContent));
            } else return new Response("password incorrect");




    }
    /**
     * @Route("/inscriptionMobile", name="inscriptionMobile")
     */
    public function register(Request $request, NormalizerInterface $normalizer, UserPasswordEncoderInterface $userPasswordEncoder)
    {
        $User = new User();
        $em = $this->getDoctrine()->getManager();
        $User->setFirstName($request->get('first_name')) ;
        $User->setLastName($request->get('last_name')) ;
        $User->setEmail($request->get('email')) ;

        $User->setPassword($userPasswordEncoder->encodePassword(
            $User,
            $request->get('password')));
        $User->setPhoneNumber($request->get('phone_number')) ;
        $User->setImage($request->get('image')) ;
        $User->setStatus($request->get('status')) ;
        $User->setRoles(["ROLE_USER"]);
        $User->setIsVerified(0 ) ;

        $em->persist($User);
        $em->flush();


        $jsonContent = $normalizer->normalize($User, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));;

    }




    /**
     * @Route("/user/liste/{id}")
     */
    public function listeuserparNom(UserRepository $UserRepository ,NormalizerInterface $Normalizer,$id)
    {
        $jsonContent=array();
        $entityManager = $this->getDoctrine()->getManager();
        $user = $entityManager->getRepository(User::class)->findAll() ;
        $output=[];
        foreach ($user as $plc){
            if($plc->getFirstName()==$id){

                $jsonContent1 = $Normalizer->normalize($plc) ;
                array_push($jsonContent,$jsonContent1);}
        }


        return new Response(json_encode($jsonContent));

    }







}
