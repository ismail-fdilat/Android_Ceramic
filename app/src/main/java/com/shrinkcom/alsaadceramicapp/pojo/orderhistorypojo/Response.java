
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userfname")
    @Expose
    private String userfname;
    @SerializedName("userlname")
    @Expose
    private String userlname;
    @SerializedName("userphone")
    @Expose
    private String userphone;
    @SerializedName("useremail")
    @Expose
    private String useremail;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("streetadd1")
    @Expose
    private String streetadd1;
    @SerializedName("streetadd2")
    @Expose
    private String streetadd2;
    @SerializedName("shipping_area")
    @Expose
    private String shipping_area;
    @SerializedName("shipping_city")
    @Expose
    private String shipping_city;
    @SerializedName("identity_document")
    @Expose
    private Object identityDocument;
    @SerializedName("delivery_status")
    @Expose
    private String delivery_status;

    @SerializedName("postalcode")
    @Expose
    private String postalcode;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("booked_id")
    @Expose
    private String bookedId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("user_ip")
    @Expose
    private Object userIp;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("shopkeeper_id")
    @Expose
    private Object shopkeeperId;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("shipping_status")
    @Expose
    private String shippingStatus;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("confirm_status")
    @Expose
    private String confirmStatus;
    @SerializedName("age_verified_status")
    @Expose
    private String ageVerifiedStatus;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("ordered_products")
    @Expose
    private List<OrderedProduct> orderedProducts = new ArrayList<OrderedProduct>();
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    };
    private final static long serialVersionUID = -2959624714719025521L;

    protected Response(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userfname = ((String) in.readValue((String.class.getClassLoader())));
        this.userlname = ((String) in.readValue((String.class.getClassLoader())));
        this.userphone = ((String) in.readValue((String.class.getClassLoader())));
        this.useremail = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.streetadd1 = ((String) in.readValue((String.class.getClassLoader())));
        this.streetadd2 = ((String) in.readValue((String.class.getClassLoader())));
        this.identityDocument = ((Object) in.readValue((Object.class.getClassLoader())));
        this.postalcode = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.bookedId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.userIp = ((Object) in.readValue((Object.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.shopkeeperId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.comment = ((Object) in.readValue((Object.class.getClassLoader())));
        this.paymentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.year = ((String) in.readValue((String.class.getClassLoader())));
        this.shipping_area = ((String) in.readValue((String.class.getClassLoader())));
        this.shipping_city = ((String) in.readValue((String.class.getClassLoader())));
        this.delivery_status = ((String) in.readValue((String.class.getClassLoader())));

        this.confirmStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.ageVerifiedStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.invoice = ((String) in.readValue((Object.class.getClassLoader())));
        in.readList(this.orderedProducts, (OrderedProduct.class.getClassLoader()));
    }

    public Response() {
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response withId(String id) {
        this.id = id;
        return this;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getShipping_area() {
        return shipping_area;
    }

    public void setShipping_area(String shipping_area) {
        this.shipping_area = shipping_area;
    }

    public String getShipping_city() {
        return shipping_city;
    }

    public void setShipping_city(String shipping_city) {
        this.shipping_city = shipping_city;
    }

    public String getUserfname() {
        return userfname;
    }

    public void setUserfname(String userfname) {
        this.userfname = userfname;
    }

    public Response withUserfname(String userfname) {
        this.userfname = userfname;
        return this;
    }

    public String getUserlname() {
        return userlname;
    }

    public void setUserlname(String userlname) {
        this.userlname = userlname;
    }

    public Response withUserlname(String userlname) {
        this.userlname = userlname;
        return this;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public Response withUserphone(String userphone) {
        this.userphone = userphone;
        return this;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Response withUseremail(String useremail) {
        this.useremail = useremail;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Response withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Response withState(String state) {
        this.state = state;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Response withCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreetadd1() {
        return streetadd1;
    }

    public void setStreetadd1(String streetadd1) {
        this.streetadd1 = streetadd1;
    }

    public Response withStreetadd1(String streetadd1) {
        this.streetadd1 = streetadd1;
        return this;
    }

    public String getStreetadd2() {
        return streetadd2;
    }

    public void setStreetadd2(String streetadd2) {
        this.streetadd2 = streetadd2;
    }

    public Response withStreetadd2(String streetadd2) {
        this.streetadd2 = streetadd2;
        return this;
    }

    public Object getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(Object identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Response withIdentityDocument(Object identityDocument) {
        this.identityDocument = identityDocument;
        return this;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public Response withPostalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Response withPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Response withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getBookedId() {
        return bookedId;
    }

    public void setBookedId(String bookedId) {
        this.bookedId = bookedId;
    }

    public Response withBookedId(String bookedId) {
        this.bookedId = bookedId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Response withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Object getUserIp() {
        return userIp;
    }

    public void setUserIp(Object userIp) {
        this.userIp = userIp;
    }

    public Response withUserIp(Object userIp) {
        this.userIp = userIp;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Response withDate(String date) {
        this.date = date;
        return this;
    }

    public Object getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(Object shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    public Response withShopkeeperId(Object shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
        return this;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Response withComment(Object comment) {
        this.comment = comment;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Response withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Response withShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
        return this;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Response withYear(String year) {
        this.year = year;
        return this;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Response withConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
        return this;
    }

    public String getAgeVerifiedStatus() {
        return ageVerifiedStatus;
    }

    public void setAgeVerifiedStatus(String ageVerifiedStatus) {
        this.ageVerifiedStatus = ageVerifiedStatus;
    }

    public Response withAgeVerifiedStatus(String ageVerifiedStatus) {
        this.ageVerifiedStatus = ageVerifiedStatus;
        return this;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Response withTransactionId(Object transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Response withOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userfname);
        dest.writeValue(userlname);
        dest.writeValue(userphone);
        dest.writeValue(useremail);
        dest.writeValue(country);
        dest.writeValue(state);
        dest.writeValue(city);
        dest.writeValue(streetadd1);
        dest.writeValue(streetadd2);
        dest.writeValue(identityDocument);
        dest.writeValue(postalcode);
        dest.writeValue(paymentMethod);
        dest.writeValue(total);
        dest.writeValue(bookedId);
        dest.writeValue(userId);
        dest.writeValue(userIp);
        dest.writeValue(date);
        dest.writeValue(shopkeeperId);
        dest.writeValue(comment);
        dest.writeValue(paymentStatus);
        dest.writeValue(shippingStatus);
        dest.writeValue(year);
        dest.writeValue(confirmStatus);
        dest.writeValue(ageVerifiedStatus);
        dest.writeValue(transactionId);
        dest.writeList(orderedProducts);
    }

    public int describeContents() {
        return  0;
    }

}
