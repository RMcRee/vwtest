#Clean up any leftovers
rm *cache *mod
# Train model
vw -d traindata.txt --oaa 29 --ngram 2 --skips 4 -b 30 --holdout_off --passes 3 --cache_file l2.cache -f predictl2.mod
# Test model
vw -t -i predictl2.mod -d vwtest.txt -p batchout.txt
# Show results. Should show label 15 five times. That is correct.
cat batchout.txt

