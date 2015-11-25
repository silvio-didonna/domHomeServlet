#include <OneWire.h>
#include <DallasTemperature.h>
#include <Servo.h>

//Temperatura
#define SERVO_WINDOW_PIN 9
//ONEWIRE----------------------------------------------------------------------START
// Data wire is plugged into port 8 on the Arduino
#define ONE_WIRE_BUS 8
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
unsigned long lastWindowServoCommand = 0;
int fanPin = 2;
bool fanOn;
int boilerPin = 5;
bool boilerOn;

//Luce
int photocell1Pin = 0;     // the first cell and 10K pulldown are connected to a0
int photocell2Pin = 1;     // the second cell and 10K pulldown are connected to a1
int photocell1Reading;     // the first analog reading from the sensor divider
int photocell2Reading;     // the second analog reading from the sensor divider
bool lightOn;
int lightPin = 13;
Servo shutter;
int shutterPin = 6;
int posServoShutter = 90;    // variable to store the servo position
int shutterOpen;
unsigned long lastShutterServoCommand = 0;

//SoftwareSerial mySerial(2, 3); // RX, TX

//Sicurezza
const int pirPin = 4;    //the digital pin connected to the PIR sensor's output
unsigned long pirStartCalibrationTime;
const int pirCalibrationTime = 30; //come da datasheet (up to 60 secs)
boolean pirCalibrated; //se true si può leggere il valore restituito dal sensore
const int flameSensorPin = 2; //the analog pin connected to the flame sensor's output

//Gestione stringa in input
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

void setup(void) {

  pirCalibrated = false;
  pirStartCalibrationTime = millis();
  pinMode(pirPin, INPUT);
  //digitalWrite(pirPin, LOW);

  pinMode(lightPin, OUTPUT);
  digitalWrite(lightPin, LOW);
  lightOn = false;
  
  shutter.attach(shutterPin);
  shutter.write(posServoShutter);
  lastShutterServoCommand = millis();
  shutterOpen = false;

  pinMode(boilerPin, OUTPUT);
  digitalWrite(boilerPin, LOW);
  boilerOn = false;

  pinMode(fanPin, OUTPUT);
  digitalWrite(fanPin, LOW);
  fanOn = false;

  window.attach(SERVO_WINDOW_PIN);
  window.write(posServoWindow);
  lastWindowServoCommand = millis();
  windowOpen = false;

  Serial.begin(115200);
  //mySerial.begin(9600);
  // reserve 100 bytes for the inputString:
  inputString.reserve(100);
  initThermometer();

}

void loop(void) {
  updateTemperature(); // ad ogni ciclo controlla se è pronto il termometro e nel caso aggiorna il valore della temperatura.

  if(millis() - lastShutterServoCommand > 500) { // se passano 500ms disattiva il servo per evitare il "buzz"
    shutter.detach();
  }

  if(millis() - lastWindowServoCommand > 500) { // se passano 500ms disattiva il servo per evitare il "buzz"
    window.detach();
  }
  

  if (stringComplete) {

    if (inputString.equals("therm1\n")) {
      Serial.println(temperature, resolution - 8); //Temperatura con le giuste cifre decimali
    }
    else if (inputString.equals("lm1\n")) {
      analogRead(photocell1Pin); //Pulisce la lettura
      photocell1Reading = analogRead(photocell1Pin);
      Serial.println(photocell1Reading);     // valore raw.
    }
    else if (inputString.equals("pir1\n")) {
      if (!pirCalibrated) {
        if ((millis() - pirStartCalibrationTime) / 1000 >= pirCalibrationTime) {
          pirCalibrated = true;
          Serial.println((digitalRead(pirPin) == HIGH) ? "true" : "false"); //se è appena scaduto il tempo per la calibrazione (eseguito solo una volta)
        }
        else {
          Serial.println("false"); //solo nella fase di calibrazione
        }
      }
      else {
        Serial.println((digitalRead(pirPin) == HIGH) ? "true" : "false"); // il sensore mantiene il valore HIGH per circa 5sec se rileva un movimento
      }
    }
    else if (inputString.equals("flame1\n")) {
      bool fire = checkFlameSensor();
      Serial.println(fire ? "true" : "false");
    }
    else if (inputString.equals("fan1\n")) {
      setFan();
      Serial.println(fanOn ? "true" : "false");
    }
    else if (inputString.equals("boiler1\n")) {
      setBoiler();
      Serial.println(boilerOn ? "true" : "false");
    }
    else if (inputString.equals("light1\n")) {
      setLight();
      Serial.println(lightOn ? "true" : "false");
    }
    else if (inputString.equals("shutter1\n")) {
      shutter.attach(shutterPin);
      setShutter();
      lastShutterServoCommand = millis();
      Serial.println(shutterOpen ? "true" : "false");
    }
    else if (inputString.equals("window1\n")) {
      window.attach(SERVO_WINDOW_PIN);
      setWindow();
      lastWindowServoCommand = millis();
      Serial.println(windowOpen ? "true" : "false");
    }
    else
      Serial.println("errore");
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

bool checkFlameSensor() {
  analogRead(flameSensorPin); //pulizia lettura
  int sensorReading = analogRead(flameSensorPin);
  // map the sensor range:
  // ex: 'long int map(long int, long int, long int, long int, long int)'
  // 0: A fire closer than 1.5 feet away.
  // 1: A fire between 1-3 feet away.
  // 2: No fire detected.
  int range = map(sensorReading, 0, 1024, 0, 3);
  return((range < 2) ? true : false);
}

void setBoiler () {
  if (boilerOn == false) { //se è spento l'accende
    digitalWrite(boilerPin, HIGH);
    boilerOn = true;
  }
  else {// se è acceso lo spegne
    digitalWrite(boilerPin, LOW);
    boilerOn = false;
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

void setShutter () {
  if (shutterOpen == false) { //se è chiusa la apre
    posServoShutter = 175;
    shutterOpen = true;
  }
  else {// se è aperta la chiude
    posServoShutter = 90;
    shutterOpen = false;
  }
  shutter.write(posServoShutter);
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


