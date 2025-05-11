package projeto.es;

public class SwapSuggestion {
    private Propriedade prop1; // Propriedade do proprietário 1 a ser trocada
    private Propriedade prop2; // Propriedade do proprietário 2 a ser trocada
    private int owner1Id;
    private int owner2Id;
    private double potentialScore; // Quão provável é a troca (similaridade de área)

    // Campos adicionais para exibir na UI
    private double owner1InitialAvgArea;
    private double owner1FinalAvgArea;
    private double owner2InitialAvgArea;
    private double owner2FinalAvgArea;


    public SwapSuggestion(Propriedade prop1, Propriedade prop2, int owner1Id, int owner2Id,
                         double potentialScore,
                          double owner1InitialAvgArea, double owner1FinalAvgArea,
                          double owner2InitialAvgArea, double owner2FinalAvgArea) {
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.owner1Id = owner1Id;
        this.owner2Id = owner2Id;
        this.potentialScore = potentialScore;
        this.owner1InitialAvgArea = owner1InitialAvgArea;
        this.owner1FinalAvgArea = owner1FinalAvgArea;
        this.owner2InitialAvgArea = owner2InitialAvgArea;
        this.owner2FinalAvgArea = owner2FinalAvgArea;
    }

    // Getters
    public Propriedade getProp1() { return prop1; }
    public Propriedade getProp2() { return prop2; }
    public int getOwner1Id() { return owner1Id; }
    public int getOwner2Id() { return owner2Id; }
    public double getPotentialScore() { return potentialScore; }
    public double getOwner1InitialAvgArea() { return owner1InitialAvgArea; }
    public double getOwner1FinalAvgArea() { return owner1FinalAvgArea; }
    public double getOwner2InitialAvgArea() { return owner2InitialAvgArea; }
    public double getOwner2FinalAvgArea() { return owner2FinalAvgArea; }


    @Override
    public String toString() {
        return String.format(
                "Trocar Propriedade %d (%.2fm², Owner %d) com Propriedade %d (%.2fm², Owner %d)\n" +
                        "  Owner %d: Área Média Inicial: %.2fm² -> Final: %.2fm²\n" +
                        "  Owner %d: Área Média Inicial: %.2fm² -> Final: %.2fm²\n" +
                        "  Score Potencial: %.2f%%",
                prop1.getObjectId(), prop1.getShape_area(), owner1Id,
                prop2.getObjectId(), prop2.getShape_area(), owner2Id,
                owner1Id, owner1InitialAvgArea, owner1FinalAvgArea,
                owner2Id, owner2InitialAvgArea, owner2FinalAvgArea,
                 potentialScore
        );
    }
}