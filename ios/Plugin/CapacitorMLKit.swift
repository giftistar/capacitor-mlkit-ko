import Foundation
import Capacitor
import MLKitTextRecognitionKorean

@objc public class CapacitorMLKit: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
    
    @objc public func ocr(_ call: CAPPluginCall) -> Void {
        let value = call.getString("value") ?? ""
        
        let koreanOptions = KoreanTextRecognizerOptions()
        let koreanTextRecognizer = TextRecognizer.textRecognizer(options:koreanOptions)
        
        
        
        
        call.resolve([
            "value": "ocr done: "
        ])
    }
    
    @objc public func barcode(_ call: CAPPluginCall) -> Void {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": "barcode done: "
        ])
    }
    
    
}
