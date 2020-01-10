package fr.bnp.homeloancalculator.infrastructure;

import fr.bnp.homeloancalculator.domain.mortgage.Borrower;
import fr.bnp.homeloancalculator.domain.mortgage.HomeloanSimulation;
import fr.bnp.homeloancalculator.domain.mortgage.MortgageProject;
import fr.bnp.homeloancalculator.domain.mortgage.ProjectType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "MORTGAGE_PROJECT")
public class MortgageProjectJPA {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "PROJECT_TYPE")
    private String projectType;

    @Column(name = "HOUSEHOLD_CHARGES")
    private double householdCharges;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="PROJECT_ID", referencedColumnName = "ID")
    private List<BorrowerJPA> borrowers;

    @Column(name = "MAX_LOAN_PAYMENT")
    private double maxLoanPayment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="PROJECT_ID", referencedColumnName = "ID")
    private List<HomeloanSimulationJPA> homeloanSimulations;

    public MortgageProjectJPA() {
    }

    public MortgageProjectJPA(MortgageProject mortgageProject) {
        this.id = mortgageProject.getId().toString();
        this.referenceId = mortgageProject.getReferenceId();
        this.projectType = mortgageProject.getProjectType().toString();
        this.householdCharges = mortgageProject.getHouseholdCharges();
        this.borrowers = mortgageProject.getBorrowers().stream().map(BorrowerJPA::new).collect(Collectors.toList());
        this.maxLoanPayment = mortgageProject.getMaxLoanPayment();
        this.homeloanSimulations = mortgageProject.getHomeloanSimulations()
                .stream().map(HomeloanSimulationJPA::new)
                .collect(Collectors.toList());
    }

    public MortgageProject toMortgageProject() {

        List<Borrower> borrowerList = borrowers.stream()
                .map(BorrowerJPA::toBorrower)
                .collect(Collectors.toList());

        List<HomeloanSimulation> homeloanSimulationList = homeloanSimulations
                .stream()
                .map(HomeloanSimulationJPA::toHomeloanSimulation)
                .collect(Collectors.toList());

        return new MortgageProject(UUID.fromString(id),
                this.referenceId,
                Enum.valueOf(ProjectType.class, this.projectType),
                this.householdCharges, borrowerList, this.maxLoanPayment, homeloanSimulationList);
    }

    public String getId() {
        return id;
    }

    public String getReferenceId() { return referenceId; }

    public double getHouseholdCharges() {
        return householdCharges;
    }

    public List<BorrowerJPA> getBorrowers() {
        return borrowers;
    }

    public double getMaxLoanPayment() {
        return maxLoanPayment;
    }

    public List<HomeloanSimulationJPA> getHomeloanSimulations() {
        return homeloanSimulations;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setHouseholdCharges(double householdCharges) {
        this.householdCharges = householdCharges;
    }

    public void setBorrowers(List<BorrowerJPA> borrowers) {
        this.borrowers = borrowers;
    }

    public void setMaxLoanPayment(double maxLoanPayment) {
        this.maxLoanPayment = maxLoanPayment;
    }

    public void setHomeloanSimulations(List<HomeloanSimulationJPA> homeloanSimulations) {
        this.homeloanSimulations = homeloanSimulations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MortgageProjectJPA that = (MortgageProjectJPA) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
