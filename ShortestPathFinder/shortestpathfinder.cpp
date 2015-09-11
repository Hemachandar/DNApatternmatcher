#include<iostream>
#include<cstring>
#include<limits>
#include<windows.h>
#include<conio.h>
#include<dos.h>
#include<stack>

#define I 1000
#define V 10
using namespace std;

int weight[V];

const string str[]={
"ARAD"
, "TIMISOARA"
, "SIBIU"
, "FAGARUS"
, "RIMNICU VILCEA"
, "PITESTI"
, "BUCHAREST"
, "LUGOJ"
, "ORZICENI"
, "HIRSOVA"
};

int findnextsource(int dis[],bool s[])
{
    int minvalue=I,minposition;
    for(int i=0;i<V;i++)
    {
        if(!s[i] && dis[i]<minvalue)
        {
            minvalue=dis[i];
            minposition=i;
        }
    }
    return minposition;
}

void print(int dis[],int VV)
{
    cout<<"The Shortest Distance from Source is : \n ";
    for(int i=0;i<VV;i++)
    {
        cout<<i<<"   -   "<<dis[i]<<endl;
    }
}

void shortestpathfinder(int graph[V][V],int src)
{
    int dis[V],path[V];
    bool s[V];
    for(int i=0;i<V;i++)
    {
        dis[i]=I;
        s[i]=false;
    }
    dis[src]=0;

    for(int i=0;i<V-1;i++)
    {
        int u=findnextsource(dis,s);
        s[u]=true;

        for(int v=0;v<V;v++)
        {
            if((!s[v]) && graph[u][v] && (dis[u]+graph[u][v])<dis[v])
            {
                  dis[v]=dis[u]+graph[u][v];
                  path[v]=u;
            }
        }
    }
    //print(dis,V);
int a[V][V],pw;

for(int i=0;i<V;i++)
{
    weight[i]=0;
    int pred=i;
    a[i][0]=pred;
    int j=1;
    while(pred!=src)
    {
        pred=path[pred];
        a[i][j++]=pred;
    }
    a[i][j]=999;
}
for(int i=0;i<V;i++)
{
    stack<int> p;
    Sleep(2000);
    cout<<" TO "<<str[i]<<" : \n";
    for(int j=0;a[i][j]!=999;j++)
    {
        p.push(a[i][j]);
    }
    pw=src;
    while(!p.empty())
    {
    weight[i]=weight[i]+graph[pw][p.top()];
    pw=p.top();
    cout<<str[p.top()]<<"("<<weight[i]<<")";
    if(str[i].compare(str[p.top()])!=0)
        cout<<" -> ";
    p.pop();
    }
cout<<endl<<endl;
}
//for(int o=0;o<V;o++)
  //  cout<<weight[o]<<endl;

}

int main()
{
    cout<<"";
     int graph[V][V] = {
                    // 1  2  3  4  5  6  7  8  9  10
                      {0, 8, 0, 2, 7, 0, 0, 0, 0, 0},//1
                      {8, 0, 1, 0, 0, 0, 0, 0, 0, 0},//2
                      {0, 1, 0, 0, 0, 4, 0, 0, 0, 0},//3
                      {2, 0, 0, 0, 4, 0, 0, 0, 0, 0},//4
                      {7, 0, 0, 4, 0, 2, 3, 1, 0, 0},//5
                      {0, 0, 4, 0, 2, 0, 0, 9, 0, 0},//6
                      {0, 0, 0, 0, 3, 0, 0, 6, 5, 0},//7
                      {0, 0, 0, 0, 1, 9, 6, 0, 4, 3},//8
                      {0, 0, 0, 0, 0, 0, 5, 4, 0, 2},//9
                      {0, 0, 0, 0, 0, 0, 0, 3, 2, 0},//10
                       };
    int i;
//    system("COLOR C");

//
    system("COLOR F");
    system("cls");

    cout<<"\n\n                                                 MAP++\n\n";
    cout<<"IN WHICH PLACE ARE YOU IN : \n\n";

    cout<<"1 ARAD \n2 TIMISOARA\n3 SIBIU\n4 FAGARUS\n5 RIMNICU VILCEA\n6 PITESTI\n7 BUCHAREST\n8 LUGOJ\n9 ORZICENI\n10 HIRSOVA\n";
    cin>>i;
    system("cls");
    cout<<"Finding the shortest path to save time ;)...";
Sleep(3000);

    system("cls");
    cout<<"\n\nSHORTEST DISTANCE AND PATH FROM "<<str[i-1]<<" TO ALL OTHER CITIES \n\n\n";
Sleep(2000);

    shortestpathfinder(graph,i-1);
    return 0;
}
