<?php

namespace App\Entity;

use App\Repository\AbonnementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
#[ORM\Entity(repositoryClass: AbonnementRepository::class)]
class Abonnement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("post:read")]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    #[Groups("post:read")]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    #[Groups("post:read")]
    private ?float $duree = null;

    #[ORM\Column]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    #[Groups("post:read")]
    private ?int $prixA = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    #[Groups("post:read")]
    private ?string $description = null;

    #[ORM\OneToMany(mappedBy: 'abonnements', targetEntity: Adherent::class, orphanRemoval: true)]
    #[Groups("post:read")]
    private Collection $adherents;

    #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    #[Groups("post:read")]
    private ?string $Name = null;

    #[ORM\Column(length: 255)]
    #[Groups("post:read")]
    private ?string $image = null;

    #[ORM\Column(length: 255)]
    private ?string $color = null;

    public function __construct()
    {
        $this->adherents = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getDuree(): ?float
    {
        return $this->duree;
    }

    public function setDuree(float $duree): self
    {
        $this->duree = $duree;

        return $this;
    }

    public function getPrixA(): ?int
    {
        return $this->prixA;
    }

    public function setPrixA(int $prixA): self
    {
        $this->prixA = $prixA;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    /**
     * @return Collection<int, Adherent>
     */
    public function getAdherents(): Collection
    {
        return $this->adherents;
    }

    public function addAdherent(Adherent $adherent): self
    {
        if (!$this->adherents->contains($adherent)) {
            $this->adherents->add($adherent);
            $adherent->setAbonnements($this);
        }

        return $this;
    }

    public function removeAdherent(Adherent $adherent): self
    {
        if ($this->adherents->removeElement($adherent)) {
            // set the owning side to null (unless already changed)
            if ($adherent->getAbonnements() === $this) {
                $adherent->setAbonnements(null);
            }
        }

        return $this;
    }

    public function getName(): ?string
    {
        return $this->Name;
    }

    public function setName(string $Name): self
    {
        $this->Name = $Name;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getColor(): ?string
    {
        return $this->color;
    }

    public function setColor(string $color): self
    {
        $this->color = $color;

        return $this;
    }
}
