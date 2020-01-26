//
//  ViewController.swift
//  MathOff
//
//  Created by Zach on 2019/12/19.
//  Copyright © 2019 Sanderson App Development. All rights reserved.
//

import UIKit;

class ViewGame: UIViewController {
    @IBOutlet var problem: UILabel!
    @IBOutlet var answer: UILabel!
    @IBOutlet var timer: UILabel!
    @IBOutlet var points: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad();
    }
    
    func press(_ num: Int) {
        answer.text = String(num);
    }
    
    @IBAction func press1() { press(1); }
    @IBAction func press2() { press(2); }
    @IBAction func press3() { press(3); }
    @IBAction func press4() { press(4); }
    @IBAction func press5() { press(5); }
    @IBAction func press6() { press(6); }
    @IBAction func press7() { press(7); }
    @IBAction func press8() { press(8); }
    @IBAction func press9() { press(9); }
    @IBAction func press0() { press(0); }
    @IBAction func pressMinus() { press(-1); }
    @IBAction func pressBack() { press(-2); }
    
}

