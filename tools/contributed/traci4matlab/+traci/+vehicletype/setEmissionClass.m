function setEmissionClass(typeID, clazz)
%setEmissionClass ets the emission class of vehicles for this type.
%   setEmissionClass(TYPEID,CLASS) Sets the emission class of vehicles of 
%   this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_EMISSIONCLASS, typeID, clazz);