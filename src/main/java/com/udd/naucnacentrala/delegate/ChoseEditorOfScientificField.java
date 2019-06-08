package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.repository.MagazineRepository;

@Component
public class ChoseEditorOfScientificField implements JavaDelegate {
    
    @Autowired
    private MagazineRepository magazineRepository;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	System.out.println("Starting task ChoseEditorOfScientificField...");
    	
        final Long magazineId = Long.valueOf(execution.getVariable("magazineId").toString());
        final Magazine magazine = magazineRepository.findById(magazineId).get();
        String editor= null;
        try {
        	editor= magazine.getScientificAreas().get(0).getEditor().toString();
        	System.out.println("Magazine has scientific area which has an editor.");
         	execution.setVariable("scientificAreaEditorId", editor);
        }catch(Exception e) {
        	System.out.println("Magazine doesn`t have a scientific area which has an editor. Setting magazins main editor in charge.");
        	execution.setVariable("scientificAreaEditorId", magazine.getMainEditor().getId().toString());
        }

        System.out.println("Ending task ChoseEditorOfScientificField...");
	}
}
