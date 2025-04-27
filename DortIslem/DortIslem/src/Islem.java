
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bilmuh
 */
public class Islem implements Serializable{
    private String isaret;
    private Double operand1,operand2;

    public Islem(String isaret, Double operand1, Double operand2) {
        this.isaret = isaret;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public Islem(String isaret, String operand1, String operand2) {
        this.isaret = isaret;
        this.operand1 = Double.parseDouble(operand1);
        this.operand2 = Double.parseDouble(operand2);
    }
    
    public String getIsaret() {
        return isaret;
    }

    public void setIsaret(String isaret) {
        this.isaret = isaret;
    }

    public Double getOperand1() {
        return operand1;
    }

    public void setOperand1(Double operand1) {
        this.operand1 = operand1;
    }

    public Double getOperand2() {
        return operand2;
    }

    public void setOperand2(Double operand2) {
        this.operand2 = operand2;
    }
    
    
}
