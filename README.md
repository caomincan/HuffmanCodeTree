# HuffmanCodeTree
This java project is based on Section 5.6 Huffman Coding Tree from book 'Data Structures& Algorithm Analysis in C++',author Clifford A Shaffer. Althroght most file compression applications didn't use Huffman coding tree, this concept is still the heart of file compression techniques. This project is try to impelemnt Huffman Coding Tree then compress any text documents for test.

##Command Line Augments
```
$ Document -demo
```
It will show the demo tree which is illustrated in book figure 5.24.The relative frequencies for eight selected lettes

| | | | | | | | | 
|---|---|---|---|---|---|---|---|
|C|D|E|K|L|M|U|Z|
|32|42|120|7|42|24|37|2|

The codes will be printed out like figure 5.31 in book

|Letter|Freq|Code|
|:---:|:---:|:---:|
|C|32|1110|
|D|42|101|
|E|120|0|
|K|7|111101|
|L|42|110|
|M|24|11111|
|U|37|100|
|Z|2|111100|
```
$ Document -g [filename.txt]
```
This command will generate key.txt from [filename.txt]. It counts each letter appeared in filename.txt then turn it into Huffman coding tree. The store key.txt contains the information of letter frequencies, which is used to compress the documents.It will output |letter|frequency|code| to screen
```
$ Document -b [filename.txt]
```
This command will compress the [filename.txt] based on key.txt. If key.txt does not exsit, then it will generate a new key.txt based on [filename.txt].
```
$ Document -r [filename.txt]
```
This command will decompress the file according to key.txt. If there is no key.txt, it does nothing.The output file will named as "refilename.txt" IMPORTANT: the file name is the original file name except key words "compressed". e.g you want to decompress [compresseddream.txt], the command should be $ Document -r dream.txt
   
