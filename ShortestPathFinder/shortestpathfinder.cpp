#include<iostream>
#include<cstring>
#include<limits>
#include<windows.h>
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

int minDistance(int distance[],bool splset[])
{
    int min=I,minposition;
    for(int i=0;i<V;i++)
    {
        if(!splset[i] && distance[i]<min)
        {
            min=distance[i];
            minposition=i;
        }
    }
    return minposition;
}

void print(int distance[],int VV)
{
    cout<<"The Shortest Distance from Source is : \n ";
    for(int i=0;i<VV;i++)
    {
        cout<<i<<"   -   "<<distance[i]<<endl;
    }
}

void shortestpathfinder(int graph[V][V],int src)
{
    int distance[V],path[V];
    bool splset[V];
    for(int i=0;i<V;i++)
    {
        distance[i]=I;
        splset[i]=false;
    }
    distance[src]=0;

    for(int i=0;i<V-1;i++)
    {
        int u=minDistance(distance,splset);
        splset[u]=true;

        for(int v=0;v<V;v++)
        {
            if((!splset[v]) && graph[u][v] && (distance[u]+graph[u][v])<distance[v])
            {
                  distance[v]=distance[u]+graph[u][v];
                  path[v]=u;
            }
        }
    }
    //print(distance,V);
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
     int graph[V][V] = {
                      {0, 8, 0, 2, 7, 0, 0, 0, 0, 0},
                      {8, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                      {0, 1, 0, 3, 0, 4, 0, 0, 0, 0},
                      {2, 0, 3, 0, 4, 5, 0, 0, 0, 0},
                      {7, 0, 0, 4, 0, 2, 3, 1, 0, 0},
                      {0, 0, 4, 5, 2, 0, 0, 9   , 0, 0},
                      {0, 0, 0, 0, 3, 0, 0, 6, 5, 0},
                      {0, 0, 0, 0, 1, 9, 6, 0, 4, 3},
                      {0, 0, 0, 0, 0, 0, 5, 4, 0, 2},
                      {0, 0, 0, 0, 0, 0, 0, 3, 2, 0},
                       };
    int i;
    cout<<"In which Section are you in : \n";
    cout<<"0 ARAD \n1 TIMISOARA\n2 SIBIU\n3 FAGARUS\n4 RIMNICU VILCEA\n5 PITESTI\n6 BUCHAREST\n";
    cout<<"7 LUGOJ\n8 ORZICENI\n9 HIRSOVA\n";
    cin>>i;
    system("cls");
    cout<<str[i]<<" TO ALL MAP\n\n\n";
    shortestpathfinder(graph,i);
    return 0;
}
