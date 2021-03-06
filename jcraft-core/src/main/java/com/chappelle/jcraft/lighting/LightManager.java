package com.chappelle.jcraft.lighting;

import com.chappelle.jcraft.Chunk;
import com.chappelle.jcraft.Vector3Int;

public interface LightManager
{
	void setBlockLight(Vector3Int location, int light);
	void removeBlockLight(Vector3Int location);
	void initChunkSunlight(Chunk chunk);
	void removeSunlight(Vector3Int location);
	void addSunlight(Vector3Int location);
	void calculateLight();
	void rebuildSunlight(Chunk chunk);
}
