package com.proxy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proxy.bean.Address;
import com.proxy.util.AddressType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

public class AddressValidator {
    private final Logger log = Logger.getLogger("AddressValidator");

    public DefaultListModel<Address> loadAddresses()throws Exception{
        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileReader reader = new FileReader("src/resources/static/addresses.json"))
        {
            try {
                JsonNode jsonArray = objectMapper.readTree(reader);
                for (JsonNode element : jsonArray) {
                    Address address = objectMapper.convertValue(element,Address.class);
                    defaultListModel.addElement(address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
        }catch(Exception ioe){
            ioe.printStackTrace();
        }


        return defaultListModel;
    }

    public String prettyPrintAllAddresses(JList<Address> addresses) throws Exception {
        StringBuilder prettyPrintAddress = new StringBuilder();
        for(int i = 0;i<addresses.getModel().getSize();i++){
            prettyPrintAddress.append(prettyPrintAddress(addresses.getModel().getElementAt(i)));
        }
        return prettyPrintAddress.toString();
    }

    public String prettyPrintAddressType(JList<Address> addresses,String typeOfAddress) throws Exception {
        log.info("The list of addresses: " + addresses.getModel().getSize() + " and the type of address: " + typeOfAddress);
        StringBuilder prettyPrintAddress = new StringBuilder();
        for(int i = 0;i<addresses.getModel().getSize();i++){
            if (addresses.getModel().getElementAt(i).getType().getName().equalsIgnoreCase(typeOfAddress))
                prettyPrintAddress.append(prettyPrintAddress(addresses.getModel().getElementAt(i)));
        }
        //log.info("AddressService :: Exit Method : prettyPrintAddressType() -> Entry Params : typeOfAddress :: {}", typeOfAddress);
        return prettyPrintAddress.toString();
    }

    public String prettyPrintAddress(Address address) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        System.out.print(gson.toJson(address));
        return gson.toJson(address);
    }

    public String printValidAddresses(JList<Address> addresses) throws Exception {
        StringBuilder prettyPrintValidAddress = new StringBuilder();
        for(int i = 0;i<addresses.getModel().getSize();i++){
            if(validateAddress(addresses.getModel().getElementAt(i))) {
                prettyPrintValidAddress.append(prettyPrintAddress(addresses.getModel().getElementAt(i)));
            }
        }
        return prettyPrintValidAddress.toString();
    }

    public boolean validateAddress(Address address){
        String regex = "\\d+";
        boolean isValidAddress = false;
        try {
            log.info("Address country name: " +address.getCountry().getName());
            if((!(address.getCountry().getName().equalsIgnoreCase("South Africa"))&&!(address.getCountry().getCode().equalsIgnoreCase("ZA")))&&
                    address.getPostalCode().matches(regex)&&address.getCountry()!=null&&(!(address.getAddressLineDetail().getLine1()!=null||address.getAddressLineDetail().getLine2()!=null))){
                isValidAddress = true;
            }
            if((address.getCountry().getName().equalsIgnoreCase("South Africa")&&address.getCountry().getCode().equalsIgnoreCase("ZA")&&address.getProvinceOrState().getName()!=null)&&
                    address.getPostalCode().matches(regex)&&address.getCountry()!=null&&(address.getAddressLineDetail().getLine1()!=null||address.getAddressLineDetail().getLine2()!=null)){
                isValidAddress = true;
            }
        }catch (Exception ex){
            System.out.println(address.getType().getName()+" in "+address.getCityOrTown()+" in "+address.getCountry().getName() +" is an invalid address.");
        }
        log.info("Address status: " + isValidAddress);
        return isValidAddress;
    }


    public static void main(String[] args) throws Exception {
        AddressValidator addressValidator = new AddressValidator();
        JList<Address> loadAllAddressesList = new JList<>(addressValidator.loadAddresses());
        loadAllAddressesList.setBounds(100,100, 30,10);

        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButtonShowAllAddresses = new JRadioButton("Show All Addresses");
        JRadioButton radioButtonShowPhysicalAddresses = new JRadioButton("Show Physical Addresses");
        JRadioButton radioButtonShowPostalAddresses = new JRadioButton("Show Postal Addresses");
        JRadioButton radioButtonShowBusinessAddresses = new JRadioButton("Show Business Addresses");
        JRadioButton radioButtonShowValidAddresses = new JRadioButton("Show Valid Addresses");
        JRadioButton radioButtonShowInValidAddresses = new JRadioButton("Show InValid Addresses");

        buttonGroup.add(radioButtonShowAllAddresses);
        buttonGroup.add(radioButtonShowBusinessAddresses);
        buttonGroup.add(radioButtonShowPostalAddresses);
        buttonGroup.add(radioButtonShowPhysicalAddresses);
        buttonGroup.add(radioButtonShowValidAddresses);
        buttonGroup.add(radioButtonShowInValidAddresses);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(6, 1));
        radioPanel.add(radioButtonShowAllAddresses);
        radioPanel.add(radioButtonShowBusinessAddresses);
        radioPanel.add(radioButtonShowPostalAddresses);
        radioPanel.add(radioButtonShowPhysicalAddresses);
        radioPanel.add(radioButtonShowValidAddresses);
        radioPanel.add(radioButtonShowInValidAddresses);

        JFrame frameDisplayAllAddresses = new JFrame();
        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(20000,800);
        splitPane.setLeftComponent(new JScrollPane(radioPanel));

        radioButtonShowAllAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowAllAddresses.isSelected()) {
                    try {
                        addressValidator.prettyPrintAllAddresses(loadAllAddressesList);
                        splitPane.setRightComponent(new JScrollPane(loadAllAddressesList));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    frameDisplayAllAddresses.add(splitPane);
                }
            }
        });

        radioButtonShowBusinessAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowBusinessAddresses.isSelected()) {
                    try {
                        addressValidator.prettyPrintAddressType(loadAllAddressesList, AddressType.BUSINESS_ADDRESS);
                        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
                        for (int i = 0; i < loadAllAddressesList.getModel().getSize(); i++) {
                            if (loadAllAddressesList.getModel().getElementAt(i).getType().getName().equalsIgnoreCase(AddressType.BUSINESS_ADDRESS)) {
                                defaultListModel.addElement(loadAllAddressesList.getModel().getElementAt(i));
                                JList<Address> businessAddressList = new JList<>(defaultListModel);
                                businessAddressList.setBounds(100, 100, 30, 10);
                                splitPane.setRightComponent(new JScrollPane(businessAddressList));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        radioButtonShowPhysicalAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowPhysicalAddresses.isSelected()) {
                    try {
                        addressValidator.prettyPrintAddressType(loadAllAddressesList, AddressType.PHYSICAL_ADDRESS);
                        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
                        for (int i = 0; i < loadAllAddressesList.getModel().getSize(); i++) {
                            if (loadAllAddressesList.getModel().getElementAt(i).getType().getName().equalsIgnoreCase(AddressType.PHYSICAL_ADDRESS)) {
                                defaultListModel.addElement(loadAllAddressesList.getModel().getElementAt(i));
                                JList<Address> physicalAddressList = new JList<>(defaultListModel);
                                physicalAddressList.setBounds(100, 100, 30, 10);
                                splitPane.setRightComponent(new JScrollPane(physicalAddressList));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        radioButtonShowPostalAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowPostalAddresses.isSelected()) {
                    try {
                        addressValidator.prettyPrintAddressType(loadAllAddressesList, AddressType.POSTAL_ADDRESS);
                        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
                        for (int i = 0; i < loadAllAddressesList.getModel().getSize(); i++) {
                            if (loadAllAddressesList.getModel().getElementAt(i).getType().getName().equalsIgnoreCase(AddressType.POSTAL_ADDRESS)) {
                                defaultListModel.addElement(loadAllAddressesList.getModel().getElementAt(i));
                                JList<Address> postalAddressList = new JList<>(defaultListModel);
                                postalAddressList.setBounds(100, 100, 30, 10);
                                splitPane.setRightComponent(new JScrollPane(postalAddressList));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        radioButtonShowValidAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowValidAddresses.isSelected()) {
                    try {
                        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
                        for (int i = 0; i < loadAllAddressesList.getModel().getSize(); i++) {
                            if(addressValidator.validateAddress(loadAllAddressesList.getModel().getElementAt(i))) {
                                defaultListModel.addElement(loadAllAddressesList.getModel().getElementAt(i));
                                JList<Address> validAddressList = new JList<>(defaultListModel);
                                validAddressList.setBounds(100, 100, 30, 10);
                                splitPane.setRightComponent(new JScrollPane(validAddressList));
                                addressValidator.printValidAddresses(validAddressList);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        radioButtonShowInValidAddresses.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (radioButtonShowInValidAddresses.isSelected()) {
                    try {
                        DefaultListModel<Address> defaultListModel = new DefaultListModel<>();
                        for (int i = 0; i < loadAllAddressesList.getModel().getSize(); i++) {
                            if(!(addressValidator.validateAddress(loadAllAddressesList.getModel().getElementAt(i)))) {
                                defaultListModel.addElement(loadAllAddressesList.getModel().getElementAt(i));
                                JList<Address> inValidAddressList = new JList<>(defaultListModel);
                                inValidAddressList.setBounds(100, 100, 30, 10);
                                splitPane.setRightComponent(new JScrollPane(inValidAddressList));
                                addressValidator.printValidAddresses(inValidAddressList);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frameDisplayAllAddresses.setTitle("Address List");
        frameDisplayAllAddresses.setSize(30000,1000);
        frameDisplayAllAddresses.setLayout( null);
        frameDisplayAllAddresses.setVisible(true);
        frameDisplayAllAddresses.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDisplayAllAddresses.add(splitPane);
    }
}