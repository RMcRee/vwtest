#Clean up any leftovers
rm *cache *mod
# Train model
vw -d traindata.txt --oaa 27 -f predictl2.mod
# Test model
vw -t -i predictl2.mod -d vwtest.txt -p batchout.txt
# Show results. Should show label 15 five times. That is correct.
cat batchout.txt

