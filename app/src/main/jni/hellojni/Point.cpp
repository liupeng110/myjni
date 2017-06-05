  #include <iostream>
  using namespace std;

    class Point
        {
            public:
                void setPoint(int x, int y) //实现setPoint函数
                {
                    xPos = x;
                    yPos = y;
                }

                void printPoint()       //实现printPoint函数
                {
                    cout<< "x = " << xPos << endl;
                    cout<< "y = " << yPos << endl;
                }

            public:
                int xPos;
                int yPos;
        };



