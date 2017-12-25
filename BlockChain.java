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
    private Pair<Block, BlockData> maxHeightBDPair;

    public class BlockData{
    	// plain old data class to record info about blocks
    	public int blockHeight;
    	public UTXOPool upool;
    	public BlockData(int height , UTXOPool pool) {
    		blockHeight = height;
    		upool = pool;
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
    	
    	Pair<Block, BlockData> genPair = new Pair<Block, BlockData>(genesisBlock, new BlockData(0, makeUTXOPool(genesisBlock))); //BlockData shouldn't be null
    	blockTree.put(genPair, genBlockList);
    	rootBlock = genesisBlock;
    	maxHeightBDPair = genPair;
;    }

    /** Get the maximum height block */
    public Block getMaxHeightBlock() {
        // IMPLEMENT THIS
    	return maxHeightBDPair.getKey();
    }

    /** Get the UTXOPool for mining a new block on top of max height block */
    public UTXOPool getMaxHeightUTXOPool() {
        // IMPLEMENT THIS 
    	return maxHeightBDPair.getValue().upool;
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
    	for (Pair<Block, BlockData> bdPair : blockTree.keySet()) {
    		if (block.getPrevBlockHash() != null && bdPair.getKey().getHash() == block.getPrevBlockHash()) {
    			int blockHeight = bdPair.getValue().blockHeight + 1;
    			if (validateBlock(blockHeight, block, bdPair)) {
    				blockTree.get(bdPair).add(block);
    				Pair<Block, BlockData> newPair = new Pair<Block, BlockData>(block, new BlockData(blockHeight, makeUTXOPool(block)));
    				blockTree.put(newPair, new ArrayList<Block>());
    				if (blockHeight > maxHeightBDPair.getValue().blockHeight) {
    					maxHeightBDPair = newPair;
    				}
    				return true;
    			}
    			else 
    				return false;
    		}
    	}
    	return false;
    }
    
    /** Add a transaction to the transaction pool */
    public void addTransaction(Transaction tx) {
        // IMPLEMENT THIS
    }
    
    private UTXOPool makeUTXOPool(Block block/*, Pair<Block, BlockData> prevBlock*/) {
    	UTXOPool pool = new UTXOPool();
//    	if (block.getHash() == rootBlock.getHash()) { // the genesis block
    		for (Transaction tx : block.getTransactions()) {
    			for (Transaction.Input in : tx.getInputs()) {
    				pool.addUTXO(new UTXO(in.prevTxHash, in.outputIndex), txPool.getTransaction(in.prevTxHash).getOutput(in.outputIndex));
    			}
    		}
//    	}
//    	else {
//    		UTXOPool deadPool = prevBlock.getValue().upool;
//    		for (Transaction tx : block.getTransactions()) {
//    			for (Transaction.Input in : tx.getInputs()) {
//    				for (UTXO u : deadPool.getAllUTXO()) {
//    					if (u.getTxHash() == in.prevTxHash) {
//    						
//    					}
//    				}
//    				pool.addUTXO(new UTXO(in.prevTxHash, in.outputIndex) );
//    			}
//    		}
//    	}
    	return pool;
    }
    
    private boolean validateBlock(int height, Block block, Pair<Block, BlockData> prevBlock) {
    	if (height <= maxHeightBDPair.getValue().blockHeight - CUT_OFF_AGE)
    		return false;
    	else {
    		TxHandler txh = new TxHandler(prevBlock.getValue().upool);
    		for (Transaction tx : block.getTransactions()) {
    			if (!txh.isValidTx(tx)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}