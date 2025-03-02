package service.rdv;

import dao.rdv.CodeDAO;
import entities.rdv.Code;

import java.util.List;

public class CodeService {
    public void ajouterCode(Code code) {
        CodeDAO.ajouterCode(code);
    }
    public List<Code> getAllCodes() {
        return CodeDAO.getAllCodes();
    }
    public void updateCode(Code code) {
        CodeDAO.updateCode(code);
    }
    public void deleteCode(int codeId) {
        CodeDAO.deleteCode(codeId);
    }


    public String getNameOfCandidat(int cinCandidat) {
        return CodeDAO.getNameOfCandidat(cinCandidat);
    }

    public String getNameOfMoniteur(int cinMoniteur) {
        return CodeDAO.getNameOfMoniteur(cinMoniteur);
    }
}
