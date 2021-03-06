package com.chappelle.jcraft.blocks;

import com.chappelle.jcraft.Block;
import com.chappelle.jcraft.BlockSkin;
import com.chappelle.jcraft.BlockSkin_TextureLocation;
import com.chappelle.jcraft.Chunk;
import com.chappelle.jcraft.Vector3Int;
import com.chappelle.jcraft.World;
import com.jme3.math.Vector3f;

public class BlockGrass extends Block
{
	public BlockGrass(int blockId)
	{
		super(blockId, new BlockSkin[] { new BlockSkin(new BlockSkin_TextureLocation(0, 0), false),
				new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
				new BlockSkin(new BlockSkin_TextureLocation(2, 0), false) });
	}

	public void onBlockPlaced(World world, Vector3Int location, Vector3f contactNormal, Vector3f cameraDirectionAsUnitVector)
	{
		world.playSound(SoundConstants.DIG_GRASS, 4);
	}

	public void onBlockRemoved(World world, Vector3Int location)
	{
		world.playSound(SoundConstants.DIG_GRASS, 4);
	}

	public void onEntityWalking(World world, Vector3Int location)
	{
//		world.playSound(SoundConstants.STEP_GRASS, 4);//FIXME: Currently not working correctly
	}

	@Override
	protected int getSkinIndex(Chunk chunk, Vector3Int location, Block.Face face)
	{
		if(chunk == null || chunk.isBlockOnSurface(location))
		{
			switch(face)
			{
				case Top:
					return 0;
				case Bottom:
					return 2;
				default: 
					return 1;
			}
		}
		return 2;
	}
}