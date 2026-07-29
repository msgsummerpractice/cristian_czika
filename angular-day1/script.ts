let loading: boolean = false;

interface DogApiResponse {
  message: string;
  status: string;
}

async function getDogImage(): Promise<void> {
  const dogImage = document.getElementById(
    "dogImage",
  ) as HTMLImageElement | null;

  if (!dogImage) {
    console.error("Dog image element not found");
    return;
  }

  loading = true;
  dogImage.hidden = false;

  try {
    dogImage.alt = "Loading...";
    const response: Response = await fetch(
      "https://dog.ceo/api/breeds/image/random",
    );
    const data: DogApiResponse = await response.json();

    dogImage.src = data.message;
    loading = false;
    dogImage.alt = "Random Dog Image";
  } catch (error: any) {
    console.error("Error fetching dog image:", error);
    loading = false;
  }
}
