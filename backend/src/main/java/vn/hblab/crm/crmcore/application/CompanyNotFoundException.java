package vn.hblab.crm.crmcore.application;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(long id) { super("Company " + id + " was not found"); }
}
