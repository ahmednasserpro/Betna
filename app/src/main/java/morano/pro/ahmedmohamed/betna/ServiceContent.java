package morano.pro.ahmedmohamed.betna;

public class ServiceContent {

    // Service icon
    private int mServiceIcon;

    // Service image
    private int mServiceImage;

    // Service name
    private String mServiceName;

    // Service description
    private String mServiceDescription;

    /**
     * Create a new ServiceContent object
     *
     * @param serviceIcon        the service icon
     * @param serviceImage       the service image
     * @param serviceName        the service name
     * @param serviceDescription the service description
     */
    public ServiceContent(int serviceIcon, int serviceImage, String serviceName,
                          String serviceDescription) {
        mServiceIcon = serviceIcon;
        mServiceImage = serviceImage;
        mServiceName = serviceName;
        mServiceDescription = serviceDescription;
    }


    /**
     * Get the icon resource id
     *
     * @return icon resource id
     */
    public int getServiceIcon() {
        return mServiceIcon;
    }

    /**
     * Get the image resource id
     *
     * @return image resource id
     */
    public int getImageResourceId() {
        return mServiceImage;
    }


    /**
     * Get the service name
     *
     * @return service name
     */
    public String getServiceName() {
        return mServiceName;
    }

    /**
     * Get the service description
     *
     * @return service description
     */
    public String getServiceDescription() {
        return mServiceDescription;
    }

}