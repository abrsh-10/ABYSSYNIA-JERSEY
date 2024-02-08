
import poster from "../../../assets/images/poster.jpg";


const Hero = () => {
  return (
   <div className="my-10 md:w-2/3 lg:w-1/2 mx-auto flex justify-center items-center">
    <div>
        <h1 className="text-center text-xl font-bold my-4">Support Top Teams At The Best Price!</h1>
    <img src={poster} alt="poster" className="w-full"/>
    </div>
</div>
  )
}

export default Hero;