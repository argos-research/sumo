function setAccel(typeID, accel)
%setAccel Sets the maximum acceleration for vehicles of this type.
%   setAccel(TYPEID,ACCEL) Sets the maximum acceleration in m/s^2 for 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_ACCEL, typeID, accel);