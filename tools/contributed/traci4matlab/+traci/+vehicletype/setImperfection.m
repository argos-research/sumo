function setImperfection(typeID, imperfection)
%setImperfection Sets the driver's imperfection for vehicles of this class.
%   setImperfection(TYPEID,IMPERFECTION) Sets the driver's imperfection
%   (dawdling) for vehicles of this class. IMPERFECTION is a double 
%   precision integer rnging from zero to one.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_IMPERFECTION, typeID, imperfection);