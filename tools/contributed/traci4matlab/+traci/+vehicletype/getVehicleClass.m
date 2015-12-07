function vehicleClass = getVehicleClass(typeID)
%getVehicleClass Returns the class of vehicles of this type.
%   vehicleClass = getVehicleClass(TYPEID) Returns the class of vehicles of 
%   this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
vehicleClass = traci.vehicletype.getUniversal(constants.VAR_VEHICLECLASS, typeID);