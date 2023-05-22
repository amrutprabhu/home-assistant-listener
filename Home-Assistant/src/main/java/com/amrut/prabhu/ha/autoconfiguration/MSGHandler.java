package com.amrut.prabhu.ha.autoconfiguration;

import com.amrut.prabhu.ha.autoconfiguration.HAHandler;
import org.springframework.stereotype.Controller;

@Controller
public class MSGHandler {

    @HAHandler(value = "Result")
    public void something(){
        System.out.println("works something");
    }

    @HAHandler(value = "Result")
    public void somethingResult(){
        System.out.println("works somethingResult");
    }

}
