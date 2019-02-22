# Cisco Config Checker

Cico Config Checker is a program that compares your Cisco switch config against assignments provided by your school or whoever. 
It analyzes the config and tells you what you're missing or if anything has been misconfigured. 

![](https://i.imgur.com/FnHnRg8.png[)

## What does it check?

As of version 1.0.0, Cisco Config Checker can check if:

* All required VLANs are present
* All VLANs are untagged on at least 1 port (Except for Vlan 1)
* VLANS that should be trunked are trunked on at least 1 port
* Specific commands have been entered

It will check these things dependent on what is specified in the assignment.

## How does it know what to check for?
 The programs bases its comparison on the `tasks.txt`. More information can be found here: (link here)
 
 
 ## Usage
 Using the program is very simple.
 
 **1.** Export your config from your physical device or from Cisco Packet Tracker. Click (link) for a guide on how to do so.
 
**2.** Import the config by clicking **Select Config File**. 

You will then see that the config has been loaded in the **Config Specification** section

![](https://i.imgur.com/9XnJ4pb.png)
  
  **3.** Select the task you want to compare against in the **Select Task** dropdown menu
  ![](https://i.imgur.com/rPkLBKf.png)
  
  You will then see that the task has been loaded in the **Task Specification** section
  
  ![](https://i.imgur.com/afRBHwS.png)
 ## Download
 
 To download the latest version, click (link here)
 
