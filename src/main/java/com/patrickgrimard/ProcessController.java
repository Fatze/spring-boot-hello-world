package com.patrickgrimard;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dostanko_VL on 28.09.2015.
 */

@RequestMapping("/Proceses")
@RestController
public class ProcessController extends LinkedHashMap<String, Process> {
    ProcessController() {
        Update();
    }

    public void Update() {
        List<String> processes = new ArrayList<String>();
        try {
            String line, pName, pId,pParentId;
            String[] pDatails;
            Process curProcess;
            java.lang.Process p = Runtime.getRuntime().exec("wmic process get Caption,ProcessId,ParentProcessId");
            //java.lang.Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    pDatails = line.substring(0).split("\\s{2,}");
                    pName = pDatails[0];//.replace("\"", "");
                    pId = pDatails[2].replace("\"","");
                    pParentId = pDatails[1].replace("\"","");
                    curProcess = getOrDefault(pId, new Process(pName, pId,pParentId));
                    this.putIfAbsent(pId, curProcess);
                }

            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        //return processes;
    }

    public Process getElementAt( int index) {
        for (Map.Entry entry : this.entrySet()) {
            if (index-- == 0) {
                return (Process)entry.getValue();
            }
        }
        return null;
    }



    @RequestMapping(value="/{id}/**",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Process index(@PathVariable("id") String id) {
        //return new Process("123", "1231231");
        return get(id);
    }
}
