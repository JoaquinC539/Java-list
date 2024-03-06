window.utils={
    setPaginator:(count)=>{
        count=Math.ceil(parseInt(count));
        const urlParams = new URLSearchParams(window.location.search);
        const pageSize = parseInt(urlParams.get("max") || 10);
        const currentPage = (parseInt(urlParams.get("offset") || 0) / pageSize) + 1;
        const totalPages=Math.ceil(count/pageSize);
        const container=document.getElementById("paginator");
        for(let page=1;page<=totalPages;page++){
            const offset=(page-1)*pageSize
            urlParams.set("max",pageSize);
            urlParams.set("offset",offset);
            const link=document.createElement("a");
            link.classList.add("relative","inline-flex","items-center",
            "px-4", "py-2", "-ml-px", "text-sm", "font-medium", "text-gray-500",
             "bg-white", "border", "border-gray-300", "cursor-default", 
             "leading-5" ,"dark:bg-gray-800" ,"dark:border-gray-600")           
            link.href=`?${urlParams.toString()}`;
            link.textContent=page;
            if(page===currentPage){
                link.classList.add("active-page");
            }
            container.appendChild(link);
        }
    }
};