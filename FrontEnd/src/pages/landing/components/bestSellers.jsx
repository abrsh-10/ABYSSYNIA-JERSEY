import { useEffect, useState } from "react";
import { apiService } from "../../../services/ApiService";

const BestSellers = () => {
  const [selectedSortOption, setSelectedSortOption] = useState("");
  const [jerseys,setJerseys] = useState([]);

  useEffect(() => {
    const fetchBestSellerProducts = async () => {
      const response = await apiService();
      setJerseys(response);
    };

    fetchBestSellerProducts();
  }, []);

  const handleSortOptionChange = (event) => {
    setSelectedSortOption(event.target.value);
    // Perform any further actions based on the selected option
  };

  return (
    <div className="w-full flex flex-col mt-5 xl:px-30 px-20">
      <div className="flex justify-between w-full">
        <p className="text-sm self-center">Showing all 10 results</p>
        <div className="flex gap-3 items-center">
          <i className="fa fa-th-large"></i>
          <i className="fa fa-list-ul"></i>
          <select
            className="w-60 border px-2 py-2"
            name="filter"
            id="filter"
            value={selectedSortOption}
            onChange={handleSortOptionChange}
          >
            <option value="best-sellers">Sort by Best Sellers</option>
            <option value="newest">Sort by latest</option>
            <option value="oldest">Sort by average rating</option>
            <option value="price-low-to-high">Sort by price: Low to High</option>
            <option value="price-high-to-low">Sort by price: High to Low</option>
            </select>
        </div>
      </div>
      {/* Product List */}
      <div className="grid md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-10 my-5">
      {
       jerseys && jerseys.map((jersey)=>(
        <div key={jersey?.id} className="bg-white rounded shadow relative gap-2.5 flex flex-col">
           <button className="absolute top-0 left-0 text-white bg-coral-red px-3 py-[1px] rounded-md">Sale!</button>
            <img src={jersey.imageUrl[0]} alt="images" />
            {jersey.categories && jersey.categories.map((category)=>(
              <p key={category} className="text-center text-slate-500">{category}</p>
            ))}
           <p className="text-center font-bold">{jersey.name}</p>
           <div className="flex justify-center gap-2">
              <p className="text-center text-coral-red font-bold">${jersey.currentPrice}</p>
              <p className="text-center text-slate-500 pb-5 text-sm line-through">${jersey.initialPrice}</p>
           </div>

           <button className="w-4/5 py-1 rounded-md self-center bg-black font-bold text-white ">Select Product Options</button>
           <button className="w-1/2 py-1.5 mb-5 rounded-md self-center font-bold bg-coral-red text-white ">Quick View</button>


        </div>
       ))
      }
      </div>
    </div>
  );
};

export default BestSellers;