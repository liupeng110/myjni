  #include <iostream>
  using namespace std;

    class Point
        {
            public:

            //Point();
           // ~Point();
                void setPoint(int x, int y) //实现setPoint函数
                {
                    xPos = x;
                    yPos = y;
                }


                int  getxPos(){ return xPos; }

                int  getyPos(){ return yPos; }

                void printPoint()       //实现printPoint函数
                {
                    cout<< "x = " << xPos << endl;
                    cout<< "y = " << yPos << endl;
                }

            private:
                int xPos;
                int yPos;




        };



