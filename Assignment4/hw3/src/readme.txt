Laura Herrle
lmh95
I discussed my homework with the ta's in the lab and discussion sections, and also discussed the validity of their suggestions with classmates at that time.
One unexpected thing I noticed was that importing strings into my trie slowed down not based on the length of the strings but on how many entries were already in the trie.
I decided to use a HashTable in my filter because it worked the best and fastest overall.  Part of the reason the trie was so slow was because it used so much memory, and I saw no reason to add a bloom filter that worked in basically the same time as the hash table.