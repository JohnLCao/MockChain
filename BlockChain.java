// Block Chain should maintain only limited block nodes to satisfy the functions
// You should not have all the blocks added to the block chain in memory 
// as it would cause a memory overflow.
import java.util.*;
import javafx.util.Pair;
import sun.security.x509.GeneralNameInterface;

public class BlockChain {
    public static final int CUT_OFF_AGE = 10;
    private TransactionPool txPool; //global transaction pool
    private HashMap<Pair<Block, BlockData>, ArrayList<Block>> blockTree = new HashMap<Pair<Block, BlockData>, ArrayList<Block>>();
    private Block rootBlock;

    public class BlockData{
    	// plain old data class to record info about blocks
    	public int blockHeight;
    	public UTXOPool upool;
    	public BlockData(int height /*, UTXOPool pool*/) {
    		blockHeight = height;
    		//upool = pool;
    	}
    }
    
    /**
     * create an empty block chain with just a genesis block. Assume {@code genesisBlock} is a valid
     * block
     */
    public BlockChain(Block genesisBlock) {
        // IMPLEMENT THIS
    	ArrayList<Block> genBlockList = new ArrayList<Block>();
    	genBlockList.add(genesisBlock);
    	
    	Pair<Block, BlockData> genPair = new Pair<Block, BlockData>(null, new BlockData(0)); //BlockData shouldn't be null
    	blockTree.put(genPair, genBlockList);
    	rootBlock = genesisBlock;
;    }

    /** Get the maximum height block */
    public Block getMaxHeightBlock() {
        // IMPLEMENT THIS
    	int maxHeight = 0;
    	Pair<Block, BlockData> maxHB = new Pair<Block, BlockData>(null, new BlockData(0));
    	Set<Pair<Block, BlockData>> blocks = blockTree.keySet();
    	for (Pair<Block, BlockData> bhPair : blocks) {
    		if (bhPair.getValue().blockHeight > maxHeight) {
    			maxHeight = bhPair.getValue().blockHeight;
    			maxHB = bhPair;
    		}
    	}
    	return maxHB.getKey(); 
    }

    /** Get the UTXOPool for mining a new block on top of max height block */
    public UTXOPool getMaxHeightUTXOPool() {
        // IMPLEMENT THIS 
    }

    /** Get the transaction pool to mine a new block */
    public TransactionPool getTransactionPool() {
        // IMPLEMENT THIS
    }

    /**
     * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
     * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
     * 
     * <p>
     * For example, you can try creating a new block over the genesis block (block height 2) if the
     * block chain height is {@code <=
     * CUT_OFF_AGE + 1}. As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block
     * at height 2.
     * 
     * @return true if block is successfully added
     */
    public boolean addBlock(Block block) {
        // IMPLEMENT THIS
    }

    /** Add a transaction to the transaction pool */
    public void addTransaction(Transaction tx) {
        // IMPLEMENT THIS
    }
}