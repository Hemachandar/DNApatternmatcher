#include<cstdio>
#include<cstring>
#include<algorithm>
#include<iostream>

using namespace std;
#define d 256 // No of possible distinct protein elements
int flag=0;

void search(string pat, string txt, int q)
{
    int M = pat.length();
    int N = txt.length();
    int i, j;
    int p = 0;
    int t = 0;
    int h = 1;

// Hash function depends on M characters of the pattern

    for (i = 0; i < M-1; i++)
        h = (h*d)%q;

    for (i = 0; i < M; i++)
    {
        p = (d*p + pat[i])%q; //Hashing of Pattern
        t = (d*t + txt[i])%q; //Hashing of Text is done here
    }

    for (i = 0; i <= N - M; i++)
    {

        if ( p == t )
        {
            for (j = 0; j < M; j++)
            {
                if (txt[i+j] != pat[j])
                    break;
            }
            if (j == M)
            {
                cout<<"DNA Pattern ("<<pat<<") found at index "<<i+1<<" \n";
                flag=1;
            }
        }

        if ( i < N-M )
        {
            t = (d*(t - txt[i]*h) + txt[i+M])%q;

            if(t < 0)
              t = (t + q);
        }
    }
}

int main()
{
    string txt,pat;
    cout<<"The DNA elements are : \n";
    cout<<"A - Adenine"<<endl;
    cout<<"G - Guanine"<<endl;
    cout<<"T - Cytosin"<<endl;
    cout<<"C - Thymine"<<endl;

    cout<<"\n\n Enter the DNA Strand : \n\t";
    cin>>txt;
    cout<<"Enter the DNA Pattern : \n\t";
    cin>>pat;
    int q = 101;  // A prime number
    search(pat, txt, q);
    if(!flag)
        cout<<"The DNA pattern is not found in any where";
    getchar();
    return 0;
}
// Time Complexity is O(MN) where N is the length of N character TEXT !!! which is very much effective but not space
