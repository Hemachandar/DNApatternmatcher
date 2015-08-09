#include<iostream>
#include<cstring>
#include<cstdio>

using namespace std;
#define loop(n) for(int i=0;i<n;i++)

	int a[40][40],s[40][40];
	string str2,str1;

void inti()
{
	loop(40)
		a[0][i]=0;
	loop(40)
		a[i][0]=0;
}

void print(int m,int n)
{
	cout<<"The Adjacency Matrix is : \n        ";
	for(int i=0;i<n;i++)
        cout<<"   "<<str2[i];
    cout<<endl;
	for(int i=0;i<=m;i++){
        if(i!=0)
        cout<<"   "<<str1[i-1];
        else
            cout<<"    ";
	for(int j=0;j<=n;j++){
        cout<<"   "<<a[i][j];
        }
        cout<<endl;
	}
}


int main()
{

	cout<<"The DNA elements are : \n";
    cout<<"A - Adenine"<<endl;
    cout<<"G - Guanine"<<endl;
    cout<<"T - Cytosin"<<endl;
    cout<<"C - Thymine"<<endl;

	cout<<"\n\n Enter the DNA Strand : \n\t";
	cin>>str1;
	cout<<"Enter the DNA Pattern to be found : \n\t";
	cin>>str2;
	int s1,s2;
	s1=str1.length();
	s2=str2.length();
	{
	//	inti();
	}
	//for(int i=0;i<s1;i++)
	//	cout<<str1[i]<<"  1  ";
	//print();
	for(int i=1;i<=s1;i++)
	{
		for(int j=1;j<=s2;j++)
		{
			if(str1[i-1]==str2[j-1])
			{
				a[i][j]+=a[i-1][j-1]+1;
				s[i][j]=10;
			}
			else{
				a[i][j]=(a[i-1][j]>a[i][j-1])?a[i-1][j]:a[i][j-1];
				s[i][j]=(a[i-1][j]>a[i][j-1])?12:11;
			}
		}
	//print(s1,s2);
	}

	if(a[s1][s2]==0){
		cout<<"No Match in 2 strings";
		return 0;
	}
	int n2=s2,n1=s1;
	string final[40];
	int k=0;
	while(n1>0 && n2>0)
	{
		if(s[n1][n2]==10){//Moves Diagonally
		//	cout<<str2[n2-1];
			final[k++]=str2[n2-1];
			n1--;
			n2--;
		}
		else if(s[n1][n2]==11)
		{//Moves to left
			n2--;
			//n1;
		}
		else
		{//Moves Upper
			n1--;
			//n2;
		}
	}
	cout<<"\n\n The matched non contiguous longest matching DNA Pattern found is : ";
	for(int i=k-1;i>=0;i--)
		cout<<final[i];
	cout<<endl;
	return 0;
}

//The Time Complexity is O(MN) the length of both the strings
