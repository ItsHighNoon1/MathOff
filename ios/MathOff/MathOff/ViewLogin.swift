//
//  ViewController.swift
//  MathOff
//
//  Created by Zach on 2019/12/19.
//  Copyright © 2019 Sanderson App Development. All rights reserved.
//

import UIKit;
import AVKit;

class ViewLogin: UIViewController {
    
    @IBOutlet var error: UILabel!;
    @IBOutlet var username: UITextField!
    @IBOutlet var password: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad();
    }
    
    @IBAction func loginButtonPressed() {
        guard let user = username.text else {
            error.text = "Username cannot be empty";
            return;
        }
        guard let pass = password.text else {
            error.text = "Password cannot be empty";
            return;
        }
        let query = "user=\(user)&password=\(pass)";
        if (!MathOff.webRequest(query, loginFinished)) {
            error.text = "Cannot resolve hostname";
        }
    }
    
    func loginFinished(response: Data?) {
        guard let safeRes = response else {
            error.text = "No data received";
            return;
        }
        let token = String(data: safeRes, encoding: .utf8);
        if (token!.count == 0) {
            error.text = "Invalid login";
        } else {
            MathOff.token = token;
            dismiss(animated: true, completion: nil);
        }
    }
    
    @IBAction func noAccount() {
        if let link = URL(string: "https://spartanweb.org/login") {
            UIApplication.shared.open(link);
        }
    }
}

