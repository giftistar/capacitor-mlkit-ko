import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorMLKitPlugin)
public class CapacitorMLKitPlugin: CAPPlugin {
    private let implementation = CapacitorMLKit()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func ocr(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.implementation.ocr(call)
        }
    }
    
    @objc func barcode(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.implementation.barcode(call)
        }
    }
    
   
    
}
