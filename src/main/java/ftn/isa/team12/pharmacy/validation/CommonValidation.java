package ftn.isa.team12.pharmacy.validation;
import java.util.regex.Pattern;

public class CommonValidation {

    private String newValue;

    public CommonValidation(){}

    public CommonValidation(String newValue){
        this.newValue= newValue;
    }

    public boolean commonValidationCheck(String currentValue){
        if(newValue.equals(currentValue) || newValue.equals("")) {
            return false;
        }
        return true;
    }

    public boolean regexValidation(String regex){
        return Pattern.matches(regex,newValue);
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
