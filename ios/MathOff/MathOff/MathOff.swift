//
//  MathOff.swift
//  MathOff
//
//  Created by Zach on 2020/01/14.
//  Copyright © 2020 Sanderson App Development. All rights reserved.
//

import UIKit;

class MathOff {
    static let endpoint = URL(string: "https://spartanweb.org/api/mathoff");
    static var token: String! = "";
    
    static func webRequest(_ content: String, _ callback: @escaping (_ response: Data?) -> Void) -> Bool {
        guard let safeUrl = endpoint else {
            return false;
        }
        var request = URLRequest(url: safeUrl);
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type");
        request.httpMethod = "POST";
        request.httpBody = content.data(using: .utf8);
        
        let task = URLSession.shared.dataTask(with: request) {
            data, response, error in
            DispatchQueue.main.async {
                let delegateObj = UIApplication.shared.delegate as! AppDelegate;
                delegateObj.webCallback(callback, data);
            }
        }
        task.resume();
        return true;
    }
}
