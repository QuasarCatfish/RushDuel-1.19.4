package net.quas.rushduel.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.util.IHasId;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SimpleJsonDataManager<T> extends SimpleJsonResourceReloadListener {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final Class<T> dataClass;
	protected Map<ResourceLocation, T> data = new HashMap<>();
	private final TreeMap<String, T> idData = new TreeMap<>();

	public SimpleJsonDataManager(String folder, Class<T> dataClass) {
		super(GSON, folder);
		this.dataClass = dataClass;
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
		data.clear();
		json.forEach((key, value) -> data.put(key, toData(key, value)));

		if (IHasId.class.isAssignableFrom(this.dataClass)) {
			data.forEach((key, value) -> {
				IHasId id = (IHasId) value;
				T t = (T) value;
				RushDuelMod.LOGGER.debug("Loaded: " + id.getId());
				idData.put(id.getId(), t);
			});
		}
	}

	protected T toData(ResourceLocation resource, JsonElement json) {
		return GSON.fromJson(json, this.dataClass);
	}

	public Map<ResourceLocation, T> getData() {
		return this.data;
	}

	public T get(ResourceLocation resource) {
		return this.data.get(resource);
	}

	public T getOrDefault(String id, T notFound) {
		return this.idData.getOrDefault(id, notFound);
	}

	public Collection<T> values() {
		return idData.values();
	}
}
