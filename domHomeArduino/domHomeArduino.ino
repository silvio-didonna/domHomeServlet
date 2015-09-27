#include <OneWire.h>
#include <DallasTemperature.h>
#include <Servo.h>


//#include <SoftwareSerial.h>
// OneWire DS18S20, DS18B20, DS1822 Temperature Example
//
// http://www.pjrc.com/teensy/td_libs_OneWire.html
//
// The DallasTemperature library can do all this work for you!
// http://milesburton.com/Dallas_Temperature_Control_Library


//Temperatura
#define SERVO_WINDOW_PIN 9
//ONEWIRE----------------------------------------------------------------------START
// Data wire is plugged into port 10 on the Arduino
#define ONE_WIRE_BUS 10
OneWire oneWire(ONE_WIRE_BUS);
// Pass our oneWire reference to Dallas Temperature.
DallasTemperature sensors(&oneWire);
DeviceAddress tempDeviceAddress;
int  resolution = 10;
unsigned long lastTempRequest = 0;
int  delayInMillis = 0;
float temperature = 0.0;
//ONEWIRE----------------------------------------------------------------------STOP
Servo window;
bool windowOpen;
int posServoWindow = 90;    // variable to store the servo position
int fanPin = 2;
bool fanOn;

//Luce
int photocell1Pin = 0;     // the first cell and 10K pulldown are connected to a0
int photocell2Pin = 1;     // the second cell and 10K pulldown are connected to a1
int photocell1Reading;     // the first analog reading from the sensor divider
int photocell2Reading;     // the second analog reading from the sensor divider
bool lightOn;
int lightPin = 13;

//unsigned long time;
//int led = 13;
//SoftwareSerial mySerial(2, 3); // RX, TX

//Gestione stringa in input
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

void setup(void) {
  pinMode(lightPin, OUTPUT);
  digitalWrite(lightPin, LOW);
  lightOn = false;

  pinMode(fanPin, OUTPUT);
  digitalWrite(fanPin, LOW);
  fanOn = false;

  window.attach(SERVO_WINDOW_PIN);
  window.write(posServoWindow);
  windowOpen = false;

  Serial.begin(115200);
  //mySerial.begin(9600);
  // reserve 100 bytes for the inputString:
  inputString.reserve(100);
  initThermometer();
}

void loop(void) {
  updateTemperature(); // ad ogni ciclo controlla se è pronto il termometro e nel caso aggiorna il valore della temperatura.

  if (stringComplete) {

    if (inputString.equals("therm1\n")) {
      Serial.println(temperature, resolution - 8); //Temperatura con le giuste cifre decimali
    }
    else if (inputString.equals("lm1\n")) {
      analogRead(photocell1Pin); //Pulisce la lettura
      photocell1Reading = analogRead(photocell1Pin);
      Serial.println(photocell1Reading);     // valore raw.
    }
    else if (inputString.equals("fan1\n")) {
      setFan();
      Serial.println(fanOn ? "true" : "false");
    }
    else if (inputString.equals("light1\n")) {
      setLight();
      Serial.println(lightOn ? "true" : "false");
    }
    else if (inputString.equals("window1\n")) {
      setWindow();
      Serial.println(windowOpen ? "true" : "false");
    }
    else
      Serial.println("errore\n");
    // clear the string:
    inputString = "";
    stringComplete = false;
  }
  else
    serialEvent(); // solo se la stringa non è completa
}

/*
  SerialEvent occurs whenever a new data comes in the
 hardware serial RX.  This routine is run between each
 time loop() runs, so using delay inside loop can delay
 response.  Multiple bytes of data may be available.
 */
void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    //mySerial.print(inChar);
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}

void setFan () {
  if (fanOn == false) { //se è spenta l'accende
    digitalWrite(fanPin, HIGH);
    fanOn = true;
  }
  else {// se è accesa la spegne
    digitalWrite(fanPin, LOW);
    fanOn = false;
  }
}

void setLight () {
  if (lightOn == false) { //se è spenta l'accende
    digitalWrite(lightPin, HIGH);
    lightOn = true;
  }
  else {// se è accesa la spegne
    digitalWrite(lightPin, LOW);
    lightOn = false;
  }
}

void setWindow () {
  if (windowOpen == false) { //se è chiusa la apre
    posServoWindow = 175;
    windowOpen = true;
  }
  else {// se è aperta la chiude
    posServoWindow = 90;
    windowOpen = false;
  }
  window.write(posServoWindow);
}

void initThermometer() {
  sensors.begin();
  sensors.getAddress(tempDeviceAddress, 0);
  sensors.setResolution(tempDeviceAddress, resolution);

  sensors.setWaitForConversion(false);
  sensors.requestTemperatures();
  delayInMillis = 750 / (1 << (12 - resolution));
  lastTempRequest = millis();
}

void updateTemperature() {
  if (millis() - lastTempRequest >= delayInMillis) // waited long enough??
  {
    temperature = sensors.getTempCByIndex(0);

    sensors.requestTemperatures();
    delayInMillis = 750 / (1 << (12 - resolution));
    lastTempRequest = millis();
  }
}


