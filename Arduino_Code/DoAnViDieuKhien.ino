#include <Servo.h>

// Khai báo đối tượng myservo dùng để điều khiển servo
Servo myservo; 

// Khai báo chân điều khiển servo
int servoPin = 9;
int sensor1 = 8;
int sensor2 = 7;
int motor = 6;

int state = 0;


void setup() {
  // put your setup code here, to run once:
  // Cài đặt chức năng điều khiển servo cho chân servoPin
  myservo.attach(servoPin);
  pinMode(sensor1, INPUT_PULLUP);
  pinMode(sensor2, INPUT_PULLUP);
  pinMode(13, OUTPUT);
  pinMode(motor,OUTPUT);
  Serial.begin(9600);
  gatServo(45);
}

void loop() {
  // put your main code here, to run repeatedly:

  // Doc tin hieu tu cong
  if (state == 0)
  {
    digitalWrite(motor,HIGH);
  }
  else
  {
    digitalWrite(motor,LOW);
  }
  int message = Serial.read();
  if (message == 0) // Neu nhan duoc tin hieu tat/mo thi thuc hien tat/mo
   {
      toggle();
   }
  
  if (state == 1) // kiem tra trang thai hoat dong cua he thong
  {
    int checkSensor1 = digitalRead(sensor1);
    int checkSensor2 = digitalRead(sensor2);
    
    // Phat hien vat can o sensor 1 thi gui thong bao den port 
  
    if (checkSensor1 == 0)
    {
      Serial.println("1");
      delay(750);
      gatServo(85);
      gatServo(45);
      
    }

    // Phat hien vat can o sensor 2 thi gui thong bao den port
    
    if (checkSensor2 == 0)
    {
      Serial.println("2");
      delay(750);
    }
  }
}


// Ham gat servo
void gatServo(int goc)
{
  // Cho servo quay một góc là goc độ
  myservo.write(goc);
  delay(2000);
}


// Ham tat mo dong co

void toggle()
{
  // turn on/off 
  if (state == 0)
  {
    state = 1;
    digitalWrite(13, HIGH);
    digitalWrite(motor,LOW);
  }
  else
  {
    state = 0;
    digitalWrite(13, LOW);
    digitalWrite(motor, HIGH);
  }
}


