package com.plima.contactlist;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Contact> contacts;
    private static Scanner scanner;
    private static int id = 0;

    public static void main(String[] args) {


        //This is the start of the program:
        //First we create the contacts Array, and then we greet the user and call the showInitialOptions method.
        contacts = new ArrayList<>();
        System.out.println("Welcome!");

        showInitialOptions();

    }

    private static void showInitialOptions(){
        System.out.println("Please select one: " +
                "\n\t1. Manage Contacts" +
                "\n\t2. Messages" +
                "\n\t3. Quit");

        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                manageContacts();
                break;
            case 2:
                manageMessages();
                break;
            default:
                break;
        }
    }

    private static void manageMessages() {
        System.out.println("Please select one:" +
                "\n\t1. Show all messages" +
                "\n\t2. Send a new message" +
                "\n\t3. Go back");

        int choice = scanner.nextInt();

        switch (choice){
            case 1:
                showAllMessages();
                break;
            case 2:
                sendNewMessage();
                break;
            default:
                showInitialOptions();
                break;
        }

    }

    private static void sendNewMessage() {

        System.out.println("Who do you want to send your message?");
        String name = scanner.next();

        //If user submits empty string start over
        if(name.equals("")){
            System.out.println("Please enter the name of your contact");
            sendNewMessage();
        }else {
           boolean doesExist = false;
           for(Contact c: contacts){
               if (c.getName().equals(name)){
                   doesExist = true;
               }
           }

           if(doesExist){
               System.out.println("Please write your message: ");
               String text = scanner.next();
               if(text.equals("")){
                   System.out.println("Your message can't be empty. Please write something.");
               }else {

                   //Incrementing the id gives a unique id to each message, then we create the message object
                   id++;
                   Message newMessage = new Message(text, name, id);

                   //Here we find the proper contact to add the message
                   for(Contact c : contacts){
                       if(c.getName().equals(name)){
                         //Get ArrayList of different messages for this particular contact
                         ArrayList<Message> newMessages = c.getMessages();
                         //then we add the new message to newMessages ArrayList
                         newMessages.add(newMessage);
                         //Save the current contact we are now
                         c.setMessages(newMessages);
                       }
                   }

               }
           }else {
               System.out.println("Contact not found");
           }
        }
        showInitialOptions();

    }

    private static void showAllMessages() {
        //Saves all the messages from all contacts into an ArrayList
        ArrayList<Message> allMessages = new ArrayList<>();

        for(Contact c : contacts){

            //Adding all messages from one contact to allMessages ArrayList
            allMessages.addAll(c.getMessages());
        }

        //Shows the details of every message if they exist, if not show message saying there are no messages
        if(allMessages.size() > 0) {
            for(Message m: allMessages){
                m.getDetails();
                System.out.println("***************");
            }
        } else {
            System.out.println("You don't have any message");
        }

        showInitialOptions();
    }

    private static void manageContacts() {
        System.out.println("Please Select One:" +
                "\n\t1. Show all contacts" +
                "\n\t2. Add a new contact" +
                "\n\t3. Search for a contact" +
                "\n\t4. Delete a contact" +
                "\n\t5. Go Back");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showAllContacts();
                break;
            case 2:
                addNewContact();
                break;
            case 3:
                searchForContact();
                break;
            case 4:
                deleteContact();
                break;
            default:
                showInitialOptions();
                break;
        }
    }

    private static void deleteContact() {
        System.out.println("Please enter the contact's name:");
        String name = scanner.next();
        if(name.equals("")) {
            System.out.println("Please enter the name");
            deleteContact();
        } else {

            boolean doesExist = false;

            for (Contact c: contacts){
                if(c.getName().equals(name)) {
                    doesExist = true;
                    contacts.remove(c);
                }
            }
            if (!doesExist){
                System.out.println("Contact not found");
            }
        }
        showInitialOptions();
    }

    private static void searchForContact() {

        System.out.println("Please enter the contact name:");
        String name = scanner.next();
        if(name.equals("")){
            System.out.println("Please enter the name");
            searchForContact();
        }else{
            boolean doesExist = false;
            for (Contact c: contacts) {
                if(c.getName().equals(name)){
                    doesExist = true;
                    c.getDetails();
                    System.out.println("***************");
                }
            }
            if(!doesExist){
                System.out.println("Contact not found");
            }
        }
        showInitialOptions();

    }

    private static void addNewContact() {
        System.out.println("Adding a new contact..." +
                "\nPlease enter the contact's name:");
        String name = scanner.next();
        System.out.println("Please enter contact's number:");
        String number = scanner.next();
        System.out.println("Please enter the contact's email:");
        String email = scanner.next();

        if(name.equals("") || number.equals("") || email.equals("")) {
            System.out.println("Please enter all of the required information");
            addNewContact();
        }else {

            //check if contact name already exists, if so, go back to the start of the method, if it doesn't, add it to contact list.
            boolean doesExist = false;
            for (Contact c: contacts){
                if (c.getName().equals(name)){
                    doesExist = true;
                }
            }

            if(doesExist){
                System.out.println("You already have a contact named " + name + "saved on this device");
                addNewContact();
            }else {
                Contact contact = new Contact(name, number, email);
                contacts.add(contact);
                System.out.println(name + " added successfully");
            }
        }

        showInitialOptions();
    }

    private static void showAllContacts() {
        //Iterate contact list, print each contact
        if(contacts.size() > 0){

            for (Contact c: contacts) {
                c.getDetails();
                //Separator for facilitating visualisation
                System.out.println("***************");
            }

            showInitialOptions();
        }else{
            System.out.println("You do not have any contact");
            showInitialOptions();
        }
    }


}
