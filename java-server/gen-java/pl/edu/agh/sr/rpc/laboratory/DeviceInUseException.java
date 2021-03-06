/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package pl.edu.agh.sr.rpc.laboratory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-07")
public class DeviceInUseException extends org.apache.thrift.TException implements org.apache.thrift.TBase<DeviceInUseException, DeviceInUseException._Fields>, java.io.Serializable, Cloneable, Comparable<DeviceInUseException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DeviceInUseException");

  private static final org.apache.thrift.protocol.TField ALERT_FIELD_DESC = new org.apache.thrift.protocol.TField("alert", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DeviceInUseExceptionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DeviceInUseExceptionTupleSchemeFactory();

  public java.lang.String alert; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ALERT((short)1, "alert");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ALERT
          return ALERT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ALERT, new org.apache.thrift.meta_data.FieldMetaData("alert", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DeviceInUseException.class, metaDataMap);
  }

  public DeviceInUseException() {
  }

  public DeviceInUseException(
    java.lang.String alert)
  {
    this();
    this.alert = alert;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeviceInUseException(DeviceInUseException other) {
    if (other.isSetAlert()) {
      this.alert = other.alert;
    }
  }

  public DeviceInUseException deepCopy() {
    return new DeviceInUseException(this);
  }

  @Override
  public void clear() {
    this.alert = null;
  }

  public java.lang.String getAlert() {
    return this.alert;
  }

  public DeviceInUseException setAlert(java.lang.String alert) {
    this.alert = alert;
    return this;
  }

  public void unsetAlert() {
    this.alert = null;
  }

  /** Returns true if field alert is set (has been assigned a value) and false otherwise */
  public boolean isSetAlert() {
    return this.alert != null;
  }

  public void setAlertIsSet(boolean value) {
    if (!value) {
      this.alert = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ALERT:
      if (value == null) {
        unsetAlert();
      } else {
        setAlert((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ALERT:
      return getAlert();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ALERT:
      return isSetAlert();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof DeviceInUseException)
      return this.equals((DeviceInUseException)that);
    return false;
  }

  public boolean equals(DeviceInUseException that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_alert = true && this.isSetAlert();
    boolean that_present_alert = true && that.isSetAlert();
    if (this_present_alert || that_present_alert) {
      if (!(this_present_alert && that_present_alert))
        return false;
      if (!this.alert.equals(that.alert))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetAlert()) ? 131071 : 524287);
    if (isSetAlert())
      hashCode = hashCode * 8191 + alert.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(DeviceInUseException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetAlert()).compareTo(other.isSetAlert());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlert()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alert, other.alert);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("DeviceInUseException(");
    boolean first = true;

    sb.append("alert:");
    if (this.alert == null) {
      sb.append("null");
    } else {
      sb.append(this.alert);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DeviceInUseExceptionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeviceInUseExceptionStandardScheme getScheme() {
      return new DeviceInUseExceptionStandardScheme();
    }
  }

  private static class DeviceInUseExceptionStandardScheme extends org.apache.thrift.scheme.StandardScheme<DeviceInUseException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DeviceInUseException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ALERT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.alert = iprot.readString();
              struct.setAlertIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DeviceInUseException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.alert != null) {
        oprot.writeFieldBegin(ALERT_FIELD_DESC);
        oprot.writeString(struct.alert);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DeviceInUseExceptionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeviceInUseExceptionTupleScheme getScheme() {
      return new DeviceInUseExceptionTupleScheme();
    }
  }

  private static class DeviceInUseExceptionTupleScheme extends org.apache.thrift.scheme.TupleScheme<DeviceInUseException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DeviceInUseException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetAlert()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetAlert()) {
        oprot.writeString(struct.alert);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DeviceInUseException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.alert = iprot.readString();
        struct.setAlertIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

