package com.chappelle.jcraft.blocks;

import com.chappelle.jcraft.Block;
import com.chappelle.jcraft.BlockSkin;
import com.chappelle.jcraft.BlockSkin_TextureLocation;
import com.chappelle.jcraft.BlockState;
import com.chappelle.jcraft.Chunk;
import com.chappelle.jcraft.Vector3Int;
import com.chappelle.jcraft.World;
import com.chappelle.jcraft.shapes.BlockShape_Door;
import com.jme3.math.Vector3f;

public class Door extends Block
{
    public static final Short VAL_SECTION_TOP = 1;
    public static final Short VAR_SECTION = 2;
    public static final Short VAR_OPEN = 3;
    public static final Short VAR_ORIENTATION = 4;

	public Door(int blockId)
	{
		super(blockId, new BlockSkin[] { new BlockSkin(new BlockSkin_TextureLocation(1, 5), true), new BlockSkin(new BlockSkin_TextureLocation(1, 6), false)});
		
		setShapes(new BlockShape_Door());
	}
	
	
    @Override
    public void onBlockPlaced(World world, Vector3Int location, Vector3f contactNormal, Vector3f cameraDirectionUnitVector)
    {
        BlockState blockState = world.getBlockState(location);
        blockState.put(VAR_OPEN, Boolean.FALSE);            
        blockState.put(VAR_ORIENTATION, cameraDirectionUnitVector);            
        
        Block bottomBlock = world.getBlock(location.subtract(new Vector3Int(0,1,0)));
        if(this == bottomBlock)
        {
            blockState.put(VAL_SECTION_TOP, Boolean.TRUE);
        }
        else//This is the bottom door, need to create the top
        {
            blockState.put(VAL_SECTION_TOP, Boolean.FALSE);
            Vector3Int topLocation = location.add(0,1,0);
            world.setBlock(topLocation, this);
            onBlockPlaced(world, topLocation, null, cameraDirectionUnitVector);
        }
    }

	@Override
	public void onNeighborRemoved(World world, Vector3Int removedBlockLocation, Vector3Int myLocation)
	{
		BlockState blockState = world.getBlockState(myLocation);
		boolean top = isTop(blockState);
		if(top && myLocation.subtract(0, 1, 0).equals(removedBlockLocation))
		{
			world.removeBlock(myLocation);
		}
		if(!top && myLocation.add(0, 1, 0).equals(removedBlockLocation))
		{
			world.removeBlock(myLocation);
		}
		
		if(!top && myLocation.subtract(0, 1, 0).equals(removedBlockLocation))//Block underneath the door
		{
			world.removeBlock(myLocation);
		}
	}
	
    private boolean isTop(BlockState blockState)
    {
        Boolean isTop = (Boolean)blockState.get(VAL_SECTION_TOP);
        return isTop != null && isTop;
    }


    @Override
    protected int getSkinIndex(Chunk chunk, Vector3Int location, Face face)
    {
        BlockState blockState = chunk.getBlockState(location);
        Boolean isTop = (Boolean)blockState.get(VAL_SECTION_TOP);
        if (isTop == null || isTop)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    
    @Override
    public boolean smothersBottomBlock()
    {
        return false;
    }
}